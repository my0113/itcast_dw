package cn.itcast.dw.realtime.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.itcast.dw.realtime.service.DashboardService;
import cn.itcast.utils.DruidHelper;
import cn.itcast.utils.RedisUtil;

/**
 * 实时数仓指标业务类
 * Created by: mengyao
 * 2019年8月29日
 */
@SuppressWarnings("all")
@Service
public class DashboardServiceImpl implements DashboardService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public List<Map<String, String>> dau() {
		List<Map<String, String>> userChartDataes = new ArrayList<Map<String, String>>();
		RedisUtil client = RedisUtil.build("bigdata-cdh01", 6379);
		String visitorJsonStr = client.get("quota_visitor");
		if (!StringUtils.isEmpty(visitorJsonStr)) {
			if (visitorJsonStr.contains("uv")) {visitorJsonStr=visitorJsonStr.replace("uv", "UV");}
			if (visitorJsonStr.contains("pv")) {visitorJsonStr=visitorJsonStr.replace("pv", "PV");}
			if (visitorJsonStr.contains("ip")) {visitorJsonStr=visitorJsonStr.replace("ip", "总访客数");}
			JSONObject visitorJson = JSONObject.parseObject(visitorJsonStr);
			if (visitorJson instanceof Map) {
				visitorJson.forEach((k,v) -> userChartDataes.add(new HashMap<String, String>(){{
					put("name", k);
					put("value", ((v instanceof Integer ? (int)v : 0))+"");
				}}));
			}
		} else {
			userChartDataes.add(new HashMap<String, String>(){{
				put("name", "PV");
				put("value", "0");
			}});
			userChartDataes.add(new HashMap<String, String>(){{
				put("name", "UV");
				put("value", "0");
			}});
			userChartDataes.add(new HashMap<String, String>(){{
				put("name", "总访客数");
				put("value", "0");
			}});
		}
		return userChartDataes;
	}

	@Override
	public List<Map<String, String>> convert() {
		List<Map<String, String>> rateChartDataes = new ArrayList<Map<String, String>>();
		RedisUtil client = RedisUtil.build("bigdata-cdh01", 6379);
		String visitorJsonStr = client.get("quota_convert");
		if (!StringUtils.isEmpty(visitorJsonStr)) {
			// bn = browseNumber
			// cn = cartNumber
			// on = orderNumber
			// pn = payNumber
			if (visitorJsonStr.contains("bn")) {visitorJsonStr=visitorJsonStr.replace("bn", "浏览");}
			if (visitorJsonStr.contains("cn")) {visitorJsonStr=visitorJsonStr.replace("cn", "加购物车");}
			if (visitorJsonStr.contains("on")) {visitorJsonStr=visitorJsonStr.replace("on", "下单");}
			if (visitorJsonStr.contains("pn")) {visitorJsonStr=visitorJsonStr.replace("pn", "付款");}
			JSONObject visitorJson = JSONObject.parseObject(visitorJsonStr);
			if (visitorJson instanceof Map) {
				visitorJson.forEach((k,v) -> rateChartDataes.add(new HashMap<String, String>(){{
					put("name", k);
					put("value", ((v instanceof Integer ? (int)v : 0))+"");
				}}));
			}
		} else {
			rateChartDataes.add(new HashMap<String, String>(){{
				put("name", "浏览");
				put("value", "0");
			}});
			rateChartDataes.add(new HashMap<String, String>(){{
				put("name", "加购物车");
				put("value", "0");
			}});
			rateChartDataes.add(new HashMap<String, String>(){{
				put("name", "下单");
				put("value", "0");
			}});
			rateChartDataes.add(new HashMap<String, String>(){{
				put("name", "付款");
				put("value", "0");
			}});
		}
		return rateChartDataes;
	}

	@Override
	public Map<String, Object> weekSale() {
		Map<String, Object> result = new HashMap<String, Object>();
		// 上周 last week SQL
		String lwSql = "SELECT SUBSTR(CAST(\"__time\" AS VARCHAR),1,10) AS d,sum(\"totalMoney\") FROM \"dws_od1\" where \"__time\" BETWEEN ((CURRENT_TIMESTAMP - INTERVAL '7' DAY)  - INTERVAL '7' DAY) AND (CURRENT_TIMESTAMP - INTERVAL '7' DAY) GROUP BY SUBSTR(CAST(\"__time\" AS VARCHAR),1,10)";
		// 本周 this week SQL
		String twSql = "SELECT SUBSTR(CAST(\"__time\" AS VARCHAR),1,10) AS d,sum(\"totalMoney\") FROM \"dws_od1\" where \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '7' DAY GROUP BY SUBSTR(CAST(\"__time\" AS VARCHAR),1,10)";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		// 上周1-7天的时间
		LinkedList<String> lwd = new LinkedList<String>();
		// 上周1-7天的数据
		LinkedList<Long> lw = new LinkedList<Long>();
		// 本周1-7天的时间
		LinkedList<String> twd = new LinkedList<String>();
		// 本周1-7天的数据
		LinkedList<Long> tw = new LinkedList<Long>();
		
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
        	connection = helper.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(lwSql);
            while (rs.next()) {
            	lwd.add(rs.getString(1));
            	lw.add(rs.getLong(2));
            }
            rs = st.executeQuery(twSql);
            while (rs.next()) {
            	twd.add(rs.getString(1));
            	tw.add(rs.getLong(2));
            }
            result.put("lw", lw);
            result.put("lwd", lwd);
            result.put("tw", tw);
            result.put("twd", twd);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	helper.close(connection, st, rs);
		}
		return result;
	}

	@Override
	public long dayOrderNum() {
		long result = 0;
		String sql = "SELECT SUM(\"count\") FROM \"dws_od1\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' DAY";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
        	connection = helper.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
            	result = rs.getLong(1);
            } 
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	helper.close(connection, st, rs);
		}
		return result;
	}

	@Override
	public long weekOrderNum() {
		long result = 0;
		String sql = "SELECT SUM(\"count\") FROM \"dws_od1\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '7' DAY";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
        	connection = helper.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
            	result = rs.getLong(1);
            } 
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	helper.close(connection, st, rs);
		}
		return result;
	}

	@Override
	public long monthOrderNum() {
		long result = 0;
		String sql = "SELECT SUM(\"count\") FROM \"dws_od1\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '30' DAY";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
        	connection = helper.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
            	result = rs.getLong(1);
            } 
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	helper.close(connection, st, rs);
		}
		return result;
	}

	@Override
	public Map<Integer, Long> areaOrderNum() {
		Map<Integer, Long> result = new HashMap<Integer, Long>();
		String sql = "SELECT areaId,SUM(\"count\") FROM \"dws_od1\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' DAY GROUP BY \"areaId\"";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = helper.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				result.put(rs.getInt(1), rs.getLong(2));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			helper.close(connection, st, rs);
		}
		return result;
	}

	@Override
	public String monthSale() {
		long sale = 0;
		String sql = "SELECT SUM(\"totalMoney\") AS \"curMonthSales\" FROM \"dws_od1\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' MONTH";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
        	connection = helper.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
            	sale = rs.getLong(1);
            } 
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	helper.close(connection, st, rs);
		}
		return amountFormat(sale);
	}

	@Override
	public String daySale() {
		long sale = 0;
		String sql = "SELECT SUM(\"totalMoney\") AS \"curMonthSales\" FROM \"dws_od1\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' DAY";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
        	connection = helper.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
            	sale = rs.getLong(1);
            } 
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	helper.close(connection, st, rs);
		}
		return amountFormat(sale);
	}

	@Override
	public Map<String, Double> hourSale() {
		Map<String, Double> result = new HashMap<String, Double>(){{
			put("0", 0d);put("1", 0d);put("2", 0d);put("3", 0d);put("4", 0d);put("5", 0d);put("6", 0d);put("7", 0d);
			put("8", 0d);put("9", 0d);put("10", 0d);put("11", 0d);put("12", 0d);put("13", 0d);put("14", 0d);put("15", 0d);
			put("16", 0d);put("17", 0d);put("18", 0d);put("19", 0d);put("20", 0d);put("21", 0d);put("22", 0d);put("23", 0d);
		}};
		String sql = "SELECT EXTRACT(HOUR FROM \"__time\") AS \"curHour\",SUM(\"totalMoney\") AS \"curHourSales\" FROM \"dws_od1\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' HOUR GROUP BY EXTRACT(HOUR FROM \"__time\")";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
        	connection = helper.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
            	result.put(rs.getInt(1)+"", rs.getDouble(2));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	helper.close(connection, st, rs);
		}
        return result;
	}

	@Override
	public Map<Integer, Long> areaOrderState() {
		Map<Integer, Long> result = new HashMap<Integer, Long>();
		String sql = "SELECT areaId, SUM(\"count\") as odn  FROM \"dws_od1\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' DAY GROUP BY \"areaId\" ORDER BY odn DESC limit 8";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = helper.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				result.put(rs.getInt(1), rs.getLong(2));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			helper.close(connection, st, rs);
		}
		return result;
	}

	@Override
	public Map<String, Long> weekOrderFinish() {
		Map<String, Long> result = new HashMap<String, Long>();
		String sql = "SELECT SUBSTR(CAST(\"__time\" AS VARCHAR),1,10) AS d,sum(\"count\") FROM \"dws_od1\" where \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '7' DAY GROUP BY SUBSTR(CAST(\"__time\" AS VARCHAR),1,10)";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = helper.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				result.put(rs.getString(1), rs.getLong(2));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			helper.close(connection, st, rs);
		}
		return result;
	}

	@Override
	public Map<Integer, Double> top4AreaSale() {
		Map<Integer, Double> result = new HashMap<Integer, Double>();
		String sql = "SELECT areaId, SUM(\"totalMoney\") as ta FROM \"dws_od1\" WHERE \"__time\" >= CURRENT_TIMESTAMP - INTERVAL '1' DAY GROUP BY \"areaId\" ORDER BY ta DESC limit 4";
		// 实例化Druid JDBC连接
		DruidHelper helper = new DruidHelper();
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			connection = helper.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				result.put(rs.getInt(1), rs.getDouble(2));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			helper.close(connection, st, rs);
		}
		return result;
	}
	
	/**
	 * 当amount的长度小于10，则进行补0
	 * @return
	 */
	public String amountFormat(Long amount) {
		String zero = "0".intern();
		String temp = null;
		int length = 10;
		String amountStr = amount.toString();
		int amountLength = amountStr.length();
		if(amountLength < length) {
			int diff = length - amountLength;
			for (int i=0; i<diff; i++) {
				if (i==0) {
					temp = zero;
				} else {
					temp = temp+zero;					
				}
			}
			amountStr = temp + amountStr;
		}
		return amountStr;
	}
	
	public static void main(String[] args) {
		DashboardServiceImpl service = new DashboardServiceImpl();
		System.out.println(service.amountFormat(1000L));
		// UV、PV、访客数
    	List<Map<String, String>> dau = service.dau();
    	// 转化率
    	List<Map<String, String>> convert = service.convert();
    	// 周销售环比分析
    	Map<String, Object> weekSale = service.weekSale();
    	// 日订单数
    	long dayOrderNum = service.dayOrderNum();
    	// 周订单数
    	long weekOrderNum = service.weekOrderNum();
    	// 月订单数
    	long monthOrderNum = service.monthOrderNum();
    	// 所有区域订单数
    	Map<Integer, Long> areaOrderNum = service.areaOrderNum();
    	// 月总销售额
    	String monthSale = service.monthSale();
    	// 日总销售额
    	String daySale = service.daySale();
    	// 今日24小时销售额
    	Map<String, Double> hourSale = service.hourSale();
    	// Top8区域订单的订单数
    	Map<Integer, Long> areaOrderState = service.areaOrderState();
    	// 本周一到本周日每天的订单数
    	Map<String, Long> weekOrderFinish = service.weekOrderFinish();
    	// Top4地区销售排行
    	Map<Integer, Double> top4AreaSale = service.top4AreaSale();
    	
    	System.out.println("UV、PV、访客数："+dau);
    	System.out.println("转化率："+convert);
    	System.out.println("周销售环比分析："+weekSale);
    	System.out.println("日订单数："+dayOrderNum);
    	System.out.println("周订单数："+weekOrderNum);
    	System.out.println("月订单数："+monthOrderNum);
    	System.out.println("所有区域订单数："+areaOrderNum);
    	System.out.println("月总销售额："+monthSale);
    	System.out.println("日总销售额："+daySale);
    	System.out.println("今日24小时销售额："+hourSale);
    	System.out.println("Top8区域订单的订单数："+areaOrderState);
    	System.out.println("本周一到本周日每天的订单数："+weekOrderFinish);
    	System.out.println("Top4地区销售排行："+top4AreaSale);
    	
	}

}
