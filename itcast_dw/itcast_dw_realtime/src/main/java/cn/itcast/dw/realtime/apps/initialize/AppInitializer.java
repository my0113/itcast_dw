package cn.itcast.dw.realtime.apps.initialize;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.itcast.dw.realtime.Configure;
import cn.itcast.dw.realtime.bean.OrderBean;
import cn.itcast.dw.realtime.bean.OrderDetailBean;
import cn.itcast.dw.realtime.bean.OrderGoodsBean;
import cn.itcast.dw.realtime.utils.JdbcHelper;
import cn.itcast.dw.realtime.utils.JsonUtil;
import cn.itcast.dw.realtime.utils.KafkaAdmin;
import cn.itcast.dw.realtime.utils.RedisUtil;

/**
 * 
 * 应用初始化器 
 * Created by: mengyao 
 * 2019年7月16日
 */
public class AppInitializer {

	private JdbcHelper jdbcHelper;

	
	public AppInitializer(JdbcHelper jdbcHelper) {
		this.jdbcHelper = jdbcHelper;
	}
	
	public static void main(String[] args) {
		JdbcHelper jdbcHelper = new JdbcHelper(Configure.mysqlUrl, Configure.mysqlUsername, Configure.mysqlPassword);
		AppInitializer appInit = new AppInitializer(jdbcHelper);
		appInit.dimInitializer();
		appInit.topicInitializer();
		
	}

	/**
	 * 如果主题不存在则初始化
	 */
	public void topicInitializer() {
		KafkaAdmin.topicIfNoExistCreate(Configure.kafkaZookeeperConnect, Configure.readTopic);
		KafkaAdmin.topicIfNoExistCreate(Configure.kafkaZookeeperConnect, Configure.writeTopic);
	}
	
	/**
	 * 初始化dim
	 */
	public void dimInitializer() {
		RedisUtil build = RedisUtil.build(Configure.redisHost, Configure.redisPort);
		List<OrderGoodsBean> ogBeans = getOrderGoods();
		ogBeans.forEach(b -> {
			build.set("dim_og_" + b.getOrderId(), JsonUtil.obj2Json(b));
		});
		build.close();
	}

	private List<OrderDetailBean> getOrderDetails() {
		String sql = "SELECT o.orderId,o.orderNo,o.shopId,o.userId,o.orderStatus,o.goodsMoney,o.deliverType,o.deliverMoney,o.totalMoney,o.realTotalMoney,o.payType,o.payFrom,o.isPay,o.areaId,o.areaIdPath,o.userName,o.userAddress,o.userPhone,o.orderScore,o.isInvoice,o.invoiceClient,o.orderRemarks,o.orderSrc,o.needPay,o.payRand,o.orderType,o.isRefund,o.isAppraise,o.cancelReason,o.rejectReason,o.rejectOtherReason,o.isClosed,o.goodsSearchKeys,o.orderunique,o.receiveTime,o.deliveryTime,o.tradeNo,o.dataFlag,o.createTime,o.settlementId,o.commissionFee,o.scoreMoney,o.useScore,o.orderCode,o.extraJson,o.orderCodeTargetID,o.noticeDeliver,o.invoiceJson,o.lockCashMoney,o.payTime,o.isBatch,o.totalPayFee,og.id,og.goodsId,og.goodsNum,og.goodsPrice,og.goodsSpecId,og.goodsSpecNames,og.goodsName,og.goodsImg,og.goodsType,og.commissionRate,og.goodsCode,og.promotionJson,a.areaName FROM wst_orders o LEFT JOIN wst_order_goods og ON og.orderId=o.orderId LEFT JOIN wst_areas a ON a.areaId=o.areaId";
		List<OrderDetailBean> beans = new ArrayList<OrderDetailBean>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = jdbcHelper.getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			OrderDetailBean bean = null;
			while (rs.next()) {
				bean = new OrderDetailBean(rs.getLong(1), rs.getString(2), rs.getLong(3), rs.getLong(4), rs.getInt(5),
						rs.getDouble(6), rs.getInt(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10),
						rs.getInt(11), rs.getString(12), rs.getInt(13), rs.getInt(14), rs.getString(15),
						rs.getString(16), rs.getString(17), rs.getString(18), rs.getInt(19), rs.getInt(20),
						rs.getString(21), rs.getString(22), rs.getInt(23), rs.getDouble(24), rs.getInt(25),
						rs.getInt(26), rs.getInt(27), rs.getInt(28), rs.getInt(29), rs.getInt(30), rs.getString(31),
						rs.getInt(32), rs.getString(33), rs.getString(34), rs.getString(35), rs.getString(36),
						rs.getString(37), rs.getInt(38), rs.getString(39), rs.getInt(40), rs.getDouble(41),
						rs.getDouble(42), rs.getInt(43), rs.getString(44), rs.getString(45), rs.getInt(46),
						rs.getInt(47), rs.getString(48), rs.getDouble(49), rs.getString(50), rs.getInt(51),
						rs.getInt(52), rs.getLong(53), rs.getLong(54), rs.getLong(55), rs.getDouble(56), rs.getInt(57),
						rs.getString(58), rs.getString(59), rs.getString(60), rs.getInt(61), rs.getDouble(62),
						rs.getString(63), rs.getString(64), rs.getString(65));
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcHelper.close(rs, ps, connection);
		}
		return beans;
	}

	private List<OrderGoodsBean> getOrderGoods() {
		String sql = "SELECT * FROM wst_order_goods";
		List<OrderGoodsBean> beans = new ArrayList<OrderGoodsBean>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = jdbcHelper.getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			OrderGoodsBean ogBean = null;
			while (rs.next()) {
				ogBean = new OrderGoodsBean(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getLong(4), rs.getDouble(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getInt(11), rs.getDouble(12), rs.getString(13), rs.getString(14));
				beans.add(ogBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcHelper.close(rs, ps, connection);
		}
		return beans;
	}

	private List<OrderBean> getOrders() {
		String sql = "SELECT * FROM wst_orders";
		List<OrderBean> beans = new ArrayList<OrderBean>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = jdbcHelper.getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			OrderBean orderBean = null;
			while (rs.next()) {
				orderBean = new OrderBean(rs.getLong(1), rs.getString(2), rs.getLong(3), rs.getLong(4), rs.getInt(5),
						rs.getDouble(6), rs.getInt(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10),
						rs.getInt(11), rs.getString(12), rs.getInt(13), rs.getInt(14), rs.getString(15),
						rs.getString(16), rs.getString(17), rs.getString(18), rs.getInt(19), rs.getInt(20),
						rs.getString(21), rs.getString(22), rs.getInt(23), rs.getDouble(24), rs.getInt(25),
						rs.getInt(26), rs.getInt(27), rs.getInt(28), rs.getInt(29), rs.getInt(30), rs.getString(31),
						rs.getInt(32), rs.getString(33), rs.getString(34), rs.getString(35), rs.getString(36),
						rs.getString(37), rs.getInt(38), rs.getString(39), rs.getInt(40), rs.getDouble(41),
						rs.getDouble(42), rs.getInt(43), rs.getString(44), rs.getString(45), rs.getInt(46),
						rs.getInt(47), rs.getString(48), rs.getDouble(49), rs.getString(50), rs.getInt(51),
						rs.getInt(52));
				beans.add(orderBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcHelper.close(rs, ps, connection);
		}
		return beans;
	}

}
