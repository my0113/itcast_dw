package cn.itcast.dw.realtime.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * Druid指标查询服务
 * Created by: mengyao
 * 2019年7月23日
 */
public class DruidService {

	static String url = "jdbc:avatica:remote:url=http://bigdata-cdh03:8888/druid/v2/sql/avatica/";
	static Properties conf = new Properties();
	
	//日订单数
	static final String DAY_ORDER_NUMBER = "dayOrderNumber";
	//月订单数
	static final String MONTH_ORDER_NUMBER = "monthOrderNumber";
	//年订单数
	static final String YEAR_ORDER_NUMBER = "yearOrderNumber";
	//周订单数
	static final String WEEK_ORDER_NUMBER = "weekOrderNumber";
	//各区域订单数
	static final String AREA_ORDER_NUMBER = "areaOrderNumber";
	//各区域日订单数占比
	static final String AREA_DAY_ORDER_FINISH = "areaDayOrderFinish";
	//周订单完成占比
	static final String AREA_WEEK_ORDER_FINISH = "areaWeekOrderFinish";
	//小时销售额
	static final String HOUR_SALES = "hourSales";
	//月总销售额
	static final String MONTH_SALES = "monthSales";
	//年总销售额
	static final String YEAR_SALES = "yearSales";
	
	@SuppressWarnings("serial")
	static Map<String, String> sqlMap = new HashMap<String, String>(){{
		put(DAY_ORDER_NUMBER, "SELECT SUM(\"count\") FROM \"dws_od\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' DAY");
		put(MONTH_ORDER_NUMBER, "SELECT SUM(\"count\") FROM \"dws_od\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' MONTH");
		put(YEAR_ORDER_NUMBER, "SELECT SUM(\"count\") FROM \"dws_od\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' YEAR");
		put(WEEK_ORDER_NUMBER, "SELECT SUM(\"count\") FROM \"dws_od\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '7' DAY");
		put(AREA_ORDER_NUMBER, "SELECT \"areaId\",SUM(\"count\") AS \"curHourSales\" FROM \"dws_od\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' DAY GROUP BY \"areaId\"");
		put(AREA_DAY_ORDER_FINISH, "SELECT \"areaId\", SUM(\"count\") AS \"areaFinshOrderNum\" FROM \"dws_od\" WHERE orderStatus=2 AND \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' DAY GROUP BY \"areaId\" ORDER BY \"areaFinshOrderNum\" DESC LIMIT 10");
		put(AREA_WEEK_ORDER_FINISH, "SELECT EXTRACT(DAY FROM \"__time\") AS \"latelyDay\", SUM(\"count\") AS \"areaFinshOrderNum\" FROM \"dws_od\" WHERE orderStatus=2 AND \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '7' DAY GROUP BY EXTRACT(DAY FROM \"__time\") ORDER BY \"areaFinshOrderNum\" DESC LIMIT 10");
		put(HOUR_SALES, "SELECT EXTRACT(HOUR FROM \"__time\") AS \"curHour\",SUM(\"totalMoney\") AS \"curHourSales\" FROM \"dws_od\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' HOUR GROUP BY EXTRACT(HOUR FROM \"__time\")");
		put(MONTH_SALES, "SELECT SUM(\"totalMoney\") AS \"curMonthSales\" FROM \"dws_od\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' MONTH");
		put(YEAR_SALES, "SELECT SUM(\"totalMoney\") AS \"curMonthSales\" FROM \"dws_od\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' YEAR");
	}};
	
	
	/**
	 * 获取Druid 8888端口SQL连接
	 * @return
	 */
	private Connection getConnection() {
		try {
			return DriverManager.getConnection(url, conf);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 释放资源
	 * @param connection
	 * @param st
	 * @param rs
	 */
	private void close(Connection connection, Statement st, ResultSet rs) {
		try {
			if (rs!=null) {
				rs.close();
			}
			if (st!=null) {
				st.close();
			}
			if (connection!=null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 日订单数
	 */
	public void getDayOrderNumber() {
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
			connection = getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sqlMap.get(DAY_ORDER_NUMBER));
            while (rs.next()) {
            	System.out.println(rs.getLong(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
			close(connection, st, rs);
		}
	}

	/**
	 * 月订单数
	 */
	public void getMonthOrderNumber() {
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
			connection = getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sqlMap.get(MONTH_ORDER_NUMBER));
            while (rs.next()) {
            	System.out.println(rs.getLong(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
			close(connection, st, rs);
		}
	}
	
	/**
	 * 年订单数
	 */
	public void getYearOrderNumber() {
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
			connection = getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sqlMap.get(YEAR_ORDER_NUMBER));
            while (rs.next()) {
            	System.out.println(rs.getLong(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
			close(connection, st, rs);
		}
	}
	
	/**
	 * 周订单数
	 */
	public void getWeekOrderNumber() {
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
			connection = getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sqlMap.get(WEEK_ORDER_NUMBER));
            while (rs.next()) {
            	System.out.println(rs.getLong(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
			close(connection, st, rs);
		}
	}
	
	/**
	 * 各区域订单数
	 * @return 
	 */
	public Map<String, Long> getAreaOrderNumber() {
		Map<String, Long> res = new HashMap<String, Long>();
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
			connection = getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sqlMap.get(AREA_ORDER_NUMBER));
            while (rs.next()) {
            	System.out.println(rs.getString(1)+"\t"+rs.getObject(2)+"\t"+rs.getObject(2).getClass());
            	res.put(rs.getString(1), rs.getLong(2));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
			close(connection, st, rs);
		}
        return res;
	}
	
	/**
	 * 各区域日订单数占比
	 * @return 
	 */
	public Map<String, Long> getAreaDayOrderFinish() {
		Map<String, Long> res = new HashMap<String, Long>();
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
			connection = getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sqlMap.get(AREA_DAY_ORDER_FINISH));
            while (rs.next()) {
            	System.out.println(rs.getString(1)+"\t"+rs.getObject(2)+"\t"+rs.getObject(2).getClass());
            	res.put(rs.getString(1), rs.getLong(2));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
			close(connection, st, rs);
		}
        return res;
	}
	
	/**
	 * 周订单完成占比
	 * @return 
	 */
	public Map<String, Long> getAreaWeekOrderFinish() {
		Map<String, Long> res = new HashMap<String, Long>();
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
			connection = getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sqlMap.get(AREA_WEEK_ORDER_FINISH));
            while (rs.next()) {
            	res.put(rs.getString(1), rs.getLong(2));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
			close(connection, st, rs);
		}
        return res;
	}
	
	/**
	 * 小时销售额
	 * @return 
	 */
	public Map<String, Long> getHourSales() {
		@SuppressWarnings("serial")
		Map<String, Long> res = new HashMap<String, Long>(){{
			put("0", 0L);put("1", 0L);put("2", 0L);put("3", 0L);put("4", 0L);put("5", 0L);put("6", 0L);put("7", 0L);
			put("8", 0L);put("9", 0L);put("10", 0L);put("11", 0L);put("12", 0L);put("13", 0L);put("14", 0L);put("15", 0L);
			put("16", 0L);put("17", 0L);put("18", 0L);put("19", 0L);put("20", 0L);put("21", 0L);put("22", 0L);put("23", 0L);
		}};
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
			connection = getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sqlMap.get(HOUR_SALES));
            while (rs.next()) {
            	res.put(rs.getLong(1)+"", rs.getLong(2));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
			close(connection, st, rs);
		}
        return res;
	}
	
	/**
	 * 月总销售额
	 */
	public void getMonthSales() {
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
			connection = getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sqlMap.get(MONTH_SALES));
            while (rs.next()) {
            	System.out.println(rs.getObject(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
			close(connection, st, rs);
		}
	}
	
	/**
	 * 年总销售额
	 */
	public void getYearSales() {
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
			connection = getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sqlMap.get(YEAR_SALES));
            while (rs.next()) {
            	System.out.println(rs.getObject(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
			close(connection, st, rs);
		}
	}
	
	public static void main(String[] args){
		DruidService service = new DruidService();
		service.getDayOrderNumber();
		service.getMonthOrderNumber();
		service.getYearOrderNumber();
		service.getWeekOrderNumber();
		service.getAreaOrderNumber();
		service.getAreaDayOrderFinish();
		service.getAreaWeekOrderFinish();
		service.getHourSales();
		service.getMonthSales();
		service.getYearSales();
	}
	
}
