package cn.itcast.dw.realtime.generater;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.itcast.dw.realtime.Configure;
import cn.itcast.dw.realtime.utils.JdbcHelper;

/**
 * 
 * Created by: mengyao
 * 2019年9月4日
 */
public class DataGeneratedApp {

	private static Logger logger = LoggerFactory.getLogger(DataGeneratedApp.class); 
	
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// ==== 订单数据 ====
	private static String[] usernames = {"张骁","邓力夫","蒲卉子","彭雅琪","罗秋蒙","邹诗雨","刘强","徐牧","付超","王彬焱","敖倩莹","潘柯佚","巫慧一","张文韬","冷天","夏圆圆","尹婷","艾米莉","姚璞阳","宋宇"};
	private static String[] userAddrs = {"北京市朝阳区大屯路3号", "北京市海淀区中关村创业大街4M咖啡","安徽省 芜湖市 芜湖县","吉林省 白山市 抚松县","广西壮族自治区 贺州地区 贺州市","安徽省 马鞍山市 金家庄区","广东省 惠州市 龙门县","新疆维吾尔自治区 省直辖行政单位 石河子市","黑龙江省 齐齐哈尔市 龙江县","上海市 市辖区 静安区","福建省 泉州市 惠安县","江苏省 无锡市 北塘区","江苏省 泰州市 兴化市","四川省 成都市 锦江区","福建省 泉州市 鲤城区","上海市 市辖区 浦东新区"};
	private static String[] userPhones = {"189****5225","152****5535","155****2375","182****8771","137****9662","182****2232","156****1679","187****9264","186****8800","186****6506","150****5112","185****1531","134****2220","130****2806","138****8179","135****5372","130****2238","186****4005","159****8894","188****6010","185****6931","188****0070","136****8632","155****5533","152****7176","159****1050"};
	private static String[] orderRemarks = {"加急，加急", "周末不送货", "任意时间配送", "仅工作时间配送"};
	private static String[] searchKeys = {"节日礼品","养生","美白","防晒","iphone x","华为","AirPods"};
	private static int[] orderSrcs = {0,1,2,3,4};
	private static int[] orderStatus = {-3,-2,-1,0,1,2};
	private static int[] shopIds = {1,2,3,4,5,6,7,8,9,10,11,12};
	private static int[] userIds = {1,2,3,4,5,6,7,8,9,10,11,12};
	private static int[] areaIds = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
	
	//==== 日志数据 ====
	private static String[] os = {"Win32","IOS","Android"};
	private static int[] types = {1,2,3};
	private static String[] referers = {
		"http://www.shop.com",
		"https://jiadian.shop.com",
		"https://list.shop.com/list.html?cat=",
		"https://baby.shop.com"
	};
	private static String[] urls = {
			"http://item.shop.com/p/43891413.html",
			"http://item.shop.com/p/89243132.html",
			"http://item.shop.com/p/34345451.html",
			"http://cart.shop.com/addToCart.html?rcd=1&pid=100000679481&pc=1&eb=1&rid=1567598858113&em=",
			"http://cart.shop.com/addToCart.html?rcd=1&pid=100000679481&pc=1&eb=1&rid=1567598858113&em=",
			"http://cart.shop.com/addToCart.html?rcd=1&pid=100000679481&pc=1&eb=1&rid=1567598858113&em=",
			"http://cart.shop.com/addToCart.html?rcd=1&pid=100000679481&pc=1&eb=1&rid=1567598858113&em=",
			"https://buy.shop.com/shopping/order/getOrderInfo",
			"https://buy.shop.com/shopping/list",
			"https://buy.shop.com/shopping/goodsIds=2133434",
			"https://order.shop.com/center/list.action",
			"https://order.shop.com/center/list.action",
			"https://order.shop.com/normal/item.action?orderid=43891413&PassKey=C3F0B668AE4A82FB5C1D77D016090218"
		};
	private static String[] guids = {
		DigestUtils.md5Hex("1"),
		DigestUtils.md5Hex("2"),
		DigestUtils.md5Hex("3"),
		DigestUtils.md5Hex("4"),
		DigestUtils.md5Hex("5"),
		DigestUtils.md5Hex("6"),
		DigestUtils.md5Hex("7"),
		DigestUtils.md5Hex("8"),
		DigestUtils.md5Hex("9"),
		DigestUtils.md5Hex("10"),
		DigestUtils.md5Hex("11"),
		DigestUtils.md5Hex("12")
	};
	
	//初始化MySQL驱动
	static {
		try {
			Class.forName(Configure.mysqlDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if(args.length < 1) {
			args = new String[] {"500"};
		}
		// 生成数据的时间间隔
		long interval = Long.valueOf(args[0]);
		JdbcHelper jdbcHelper = new JdbcHelper(Configure.mysqlUrl, Configure.mysqlUsername, Configure.mysqlPassword);
		Connection connection = jdbcHelper.getConnection();
		long count = 1;
		while (count < 10000) {
			try {
				insertLogs(connection);
				logger.info("==== 新增log数据：{}条。 ====", count);
				insertOrders(connection, count);
				logger.info("==== 新增order数据：{}条。 ====", count);
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count ++;
		}
		jdbcHelper.close(null, null, connection);
	}
	
	/**
	 * 生成log数据
	 * @param connection
	 */
	private static void insertLogs(Connection connection) {
		String sql = "INSERT INTO `itcast_shop`.`itcast_logs` (id,url,referer,type,guid,session_id,ip,track_time,province_id,platform) VALUES(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps = connection.prepareStatement(sql);
			ps.setString(1, getFixLenthString(17));
			ps.setString(2, urls[(int)(Math.random() * 12 + 1)]);
			ps.setString(3, referers[(int)(Math.random() * 3 + 1)]);
			ps.setString(4, types[(int)(Math.random() * 2 + 1)]+"");
			ps.setString(5, guids[(int)(Math.random() * 11 + 1)]);
			ps.setString(6, guids[(int)(Math.random() * 11 + 1)]);
			ps.setString(7, randomIp());
			ps.setString(8, formater.format(new Date()));
			ps.setString(9, areaIds[(int)(Math.random() * 35 + 1)]+"");
			ps.setString(10, os[(int)(Math.random() * 2 + 1)]);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null!=ps) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 生成order数据
	 * @param connection
	 */
	private static void insertOrders(Connection connection, long incr) {
		long maxId = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "INSERT INTO `itcast_shop`.`itcast_orders`(`orderId`,`orderNo`,`shopId`,`userId`,`orderStatus`,`goodsMoney`,`deliverType`,`deliverMoney`,`totalMoney`,`realTotalMoney`,`payType`,`payFrom`,`isPay`,`areaId`,`areaIdPath`,`userName`,`userAddress`,`userPhone`,`orderScore`,`isInvoice`,`invoiceClient`,`orderRemarks`,`orderSrc`,`needPay`,`payRand`,`orderType`,`isRefund`,`isAppraise`,`cancelReason`,`rejectReason`,`rejectOtherReason`,`isClosed`,`goodsSearchKeys`,`orderunique`,`receiveTime`,`deliveryTime`,`tradeNo`,`dataFlag`,`createTime`,`settlementId`,`commissionFee`,`scoreMoney`,`useScore`,`orderCode`,`extraJson`,`orderCodeTargetId`,`noticeDeliver`,`invoiceJson`,`lockCashMoney`,`payTime`,`isBatch`,`totalPayFee`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			ps = connection.prepareStatement("SELECT MAX(orderId) FROM itcast_orders");
			rs = ps.executeQuery();
			if (rs.next()) {
				maxId = rs.getLong(1);
			}
			if (maxId > 0) {
				ps.clearWarnings();
				ps = connection.prepareStatement(sql);
				ps.setLong(1, maxId+incr);
				ps.setString(2, (maxId+incr)+getFixLenthString(5));
				ps.setLong(3, shopIds[(int)(Math.random() * 11 + 1)]);
				ps.setLong(4, userIds[(int)(Math.random() * 11 + 1)]);
				ps.setInt(5, orderStatus[(int)(Math.random() * 5 + 1)]);
				double money = Math.random()*999999;
				ps.setDouble(6, money);
				ps.setInt(7, incr/2==0?0:1);
				ps.setDouble(8, money);
				ps.setDouble(9, money);
				ps.setDouble(10, money);
				int pay = userIds[(int)(Math.random() * 5 + 1)];
				ps.setInt(11, pay);
				ps.setString(12, pay+"");
				ps.setInt(13, incr/2==0?0:1);
				int areaId = areaIds[(int)(Math.random() * 34 + 1)];
				ps.setInt(14, areaId);
				ps.setString(15, areaId+"");
				ps.setString(16, usernames[(int)(Math.random() * 19 + 1)]);
				ps.setString(17, userAddrs[(int)(Math.random() * 14 + 1)]);
				ps.setString(18, userPhones[(int)(Math.random() * 24 + 1)]);
				ps.setInt(19, ((100 - 0) * new Random().nextInt()));
				ps.setInt(20, incr/2==0?0:1);
				ps.setString(21, null);
				ps.setString(22, orderRemarks[(int)(Math.random() * 3 + 1)]);
				ps.setInt(23, orderSrcs[(int)(Math.random() * 4 + 1)]);
				ps.setDouble(24, Math.floor(Math.random()+1d));
				ps.setInt(25, incr/2==0?0:1);
				ps.setInt(26, 0);
				ps.setInt(27, incr/2==0?0:1);
				ps.setInt(28, incr/2==0?0:1);
				ps.setInt(29, 0);
				ps.setInt(30, 0);
				ps.setString(31, null);
				ps.setInt(32, 0);
				ps.setString(33, searchKeys[(int)(Math.random() * 6 + 1)]);
				UUID uuid = UUID.randomUUID();
				ps.setString(34, uuid.toString());
				long currentTimeMillis = System.currentTimeMillis();
				ps.setString(35, formater.format(new Date(currentTimeMillis)));
				ps.setString(36, formater.format(new Date(currentTimeMillis+1000)));
				ps.setString(37, uuid.toString());
				ps.setInt(38, 1);
				ps.setString(39, formater.format(new Date(currentTimeMillis+30000)));
				ps.setInt(40, incr/2==0?0:1);
				ps.setDouble(41, new Random().nextInt(20)%(20-2+1) + 2);
				ps.setDouble(42, 0.00);
				ps.setInt(43, 0);
				ps.setString(44, "order");
				ps.setString(45, null);
				ps.setInt(46, 0);
				ps.setInt(47, 0);
				ps.setString(48, null);
				ps.setDouble(49, 0.00);
				ps.setString(50, null);
				ps.setInt(51, 0);
				ps.setInt(52, 1);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null!=ps) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取固定的长度的随机数
	 * @param strLength
	 * @return
	 */
	private static String getFixLenthString(int strLength) {
	    Random rm = new Random();  
	    // 获得随机数  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	    // 将获得的获得随机数转化为字符串  
	    String fixLenthString = String.valueOf(pross);  
	    // 返回固定的长度的随机数
	    if (strLength + 1 > fixLenthString.length()) {
	    	return fixLenthString.substring(1, strLength).replace(".", "");			
		} else {
			return fixLenthString.substring(1, strLength + 1).replace(".", "");
		}
	}
	
	/**
	 * 生成IP
	 * @return
	 */
	private static String randomIp() {
		// 需要排除监控的ip范围
		int[][] range = { { 607649792, 608174079 }, // 36.56.0.0-36.63.255.255
				{ 1038614528, 1039007743 }, // 61.232.0.0-61.237.255.255
				{ 1783627776, 1784676351 }, // 106.80.0.0-106.95.255.255
				{ 2035023872, 2035154943 }, // 121.76.0.0-121.77.255.255
				{ 2078801920, 2079064063 }, // 123.232.0.0-123.235.255.255
				{ -1950089216, -1948778497 }, // 139.196.0.0-139.215.255.255
				{ -1425539072, -1425014785 }, // 171.8.0.0-171.15.255.255
				{ -1236271104, -1235419137 }, // 182.80.0.0-182.92.255.255
				{ -770113536, -768606209 }, // 210.25.0.0-210.47.255.255
				{ -569376768, -564133889 }, // 222.16.0.0-222.95.255.255
		};
		Random rdint = new Random();
		int index = rdint.nextInt(10);
		String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
		return ip;
	}
	
	/**
	 * 将十进制转换成IP地址
	 * @param ip
	 * @return
	 */
	public static String num2ip(int ip) {
		int[] b = new int[4];
		String x = "";
		b[0] = (int) ((ip >> 24) & 0xff);
		b[1] = (int) ((ip >> 16) & 0xff);
		b[2] = (int) ((ip >> 8) & 0xff);
		b[3] = (int) (ip & 0xff);
		x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + 
		Integer.toString(b[2]) + "." + Integer.toString(b[3]);
		return x;
	}
	
}
