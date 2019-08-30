package cn.itcast.dw.realtime.datatransfer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;

import cn.itcast.dw.realtime.bean.OrderGoodsBean;
import cn.itcast.dw.realtime.utils.JsonUtil;

public class CanalTools {

	private static CanalTools canalClient;
	private CanalConnector conn;
	private String host;
	private int port = 11111;

	CanalTools(String host, int port) {
		this.host = host;
		this.port = port;
		initializer();
	}

	private void initializer() {
		this.conn = CanalConnectors.newSingleConnector(new InetSocketAddress("bigdata-cdh01", 11111), "example", "root",
				"123456");
	}

	public static CanalTools build(String host, int port) {
		if (canalClient == null) {
			canalClient = new CanalTools(host, port);
		}
		return canalClient;
	}

	public void monitorTbl(String tableName) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int batchSize = 100;
				int emptyCount = 1;
				try {
					conn.connect();
					conn.subscribe(".*\\..*");//
					conn.rollback();
					int totalCount = 120; // 循环次数
					while (totalCount > emptyCount) {
						// 获取数据
						Message message = conn.getWithoutAck(batchSize);
						long id = message.getId();
						int size = message.getEntries().size();
						if (id == -1 || size == 0) {
							// 没有读取到任何数据
						} else {
							// 有数据，那么解析binlog日志
							checkTbl(message.getEntries(), emptyCount);
							emptyCount++;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					conn.disconnect();
				}
			}
		}).start();

	}

	public void checkTbl(List<CanalEntry.Entry> entries, int emptyCount) throws IOException {
		for (CanalEntry.Entry entry : entries) {
			// mysql的事务开始前 和事务结束后的内容 不要的
			if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN
					|| entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
				continue;
			}
			// 如果不是以上的事务，那么解析binlog
			CanalEntry.RowChange rowChange = null;
			try {
				rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 获取关键字段 哪一个数据库有事务发生 那张表 、 增加 删除 修改
			CanalEntry.EventType eventType = rowChange.getEventType();// 操作的是insert 还是delete 还是update
			String logfileName = entry.getHeader().getLogfileName();// 当前读取的是哪一个binlog文件
			long logfileOffset = entry.getHeader().getLogfileOffset();// 当前读取的binlog文件位置
			String dbName = entry.getHeader().getSchemaName();// 当前操作的mysql数据库
			String tableName = entry.getHeader().getTableName();// 当前操作的是哪一张表

			if (tableName.equals("itcast_goods_product")) {

				Map<String, String> orderLists = new HashMap<String, String>();
				List<CanalEntry.RowData> lists = rowChange.getRowDatasList();
				for (CanalEntry.RowData row : lists) {
					OrderGoodsBean goodsBean = new OrderGoodsBean();
					row.getAfterColumnsList().forEach(col -> {
						if ("id".equals(col.getName()))
							goodsBean.setId(Long.parseLong(col.getValue()));
					});
					orderLists.put(String.valueOf(goodsBean.getOrderId()), JsonUtil.obj2Json(goodsBean));

				}
				// RedisUtil.build("", 6379).saveAsList(orderLists);
			}
		}
	}

	public static void main(String[] args) {
		CanalTools.build("bigdata-cdh01", 11111).monitorTbl("itcast_order_goods");
	}

}
