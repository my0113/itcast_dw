package cn.itcast.dw.realtime.datatransfer;

import java.net.InetSocketAddress;
import java.util.List;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;

/**
 * 
 * Created by: mengyao
 * 2019年8月1日
 */
public class ClientTest {
	public static void main(String args[]) {
		CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.10.140", 11111),
				"example", "root", "123456");
		int batchSize = 50;
		int emptyCount = 0;
		try {
			connector.connect();
			connector.subscribe("wst_shop\\..*");
			connector.rollback();
			int totalEmptyCount = 10000;
			while (emptyCount < totalEmptyCount) {
				Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
				long batchId = message.getId();
				int size = message.getEntries().size();
				if (batchId == -1 || size == 0) {
					emptyCount++;
					System.out.println("empty count : " + emptyCount);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				} else {
					emptyCount = 0;
					printEntry(message.getEntries());
				}
				connector.ack(batchId); // 提交确认
			}

			System.out.println("empty too many times, exit");
		} finally {
			connector.disconnect();
		}
	}

	private static void printEntry(List<Entry> entrys) {
		for (Entry entry : entrys) {
			if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN
					|| entry.getEntryType() == EntryType.TRANSACTIONEND) {
				continue;
			}
			RowChange rowChage = null;
			try {
				rowChage = RowChange.parseFrom(entry.getStoreValue());
			} catch (Exception e) {
				throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
			}
			EventType eventType = rowChage.getEventType();
			System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
					entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
					entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));
			if (eventType == EventType.INSERT) {
				rowChage.getRowDatasList().forEach(row -> {
					row.getBeforeColumnsList().forEach(col->{
						System.out.println("修改前的值："+col.getName()+""+col.getValue());
					});
					row.getAfterColumnsList().forEach(col->{
						System.out.println("修改后的值："+col.getName()+""+col.getValue());
					});
				});
			}
			if (eventType == EventType.DELETE) {
				rowChage.getRowDatasList().forEach(row -> {
					row.getBeforeColumnsList().forEach(col->{
						System.out.println("修改前的值："+col.getName()+""+col.getValue());
					});
					row.getAfterColumnsList().forEach(col->{
						System.out.println("修改后的值："+col.getName()+""+col.getValue());
					});
				});
			}
			if (eventType == EventType.UPDATE) {
				rowChage.getRowDatasList().forEach(row -> {
					row.getBeforeColumnsList().forEach(col->{
						System.out.println("修改前的值："+col.getName()+""+col.getValue());
					});
					row.getAfterColumnsList().forEach(col->{
						System.out.println("修改后的值："+col.getName()+""+col.getValue());
					});
				});
			}
			if (eventType == EventType.QUERY) {
				System.out.println("==== query!");
			}

//			for (RowData rowData : rowChage.getRowDatasList()) {
//				if (eventType == EventType.DELETE) {
//					printColumn(rowData.getBeforeColumnsList());
//				} else if (eventType == EventType.INSERT) {
//					printColumn(rowData.getAfterColumnsList());
//				} else {
//					System.out.println("-------&gt; before");
//					printColumn(rowData.getBeforeColumnsList());
//					System.out.println("-------&gt; after");
//					printColumn(rowData.getAfterColumnsList());
//				}
//			}
		}
	}

	private static void printColumn(List<Column> columns) {
		for (Column column : columns) {
			System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
		}
	}
}


