package cn.itcast.dw.realtime.generater;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.sql.*;

public class DataBaseHelper {

	static String URLr = "jdbc:mysql://localhost:3306/wst_shop?useUnicode=true&characterEncoding=utf8";
	static String URLw = "jdbc:mysql://localhost:3306/wst_shop?useUnicode=true&characterEncoding=utf8";
	static String USERNAME = "root";
	static String PASSWORD = "000000";
	static String driver = "com.mysql.jdbc.Driver";

	private Connection conn;
	private PreparedStatement pstmt;

	public boolean closeConnection() {
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean openReadConnection() {
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(URLr, USERNAME, PASSWORD);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean openWriteConnection() {
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(URLw, USERNAME, PASSWORD);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean doSql(String sql, ArrayList<Object> para) {
		int i = 1;
		Iterator<Object> iter;
		if (para != null)
			iter = para.iterator();
		else
			iter = null;
		try {
			// 准备执行语句
			pstmt = conn.prepareStatement(sql);
			// 解析参数列表
			if (iter != null)
				while (iter.hasNext()) {
					Object p = iter.next();
					pstmt.setObject(i++, p);
				}
			pstmt.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public ResultSet doQuery(String sql, ArrayList<Object> para) {
		int i = 1;
		Iterator<Object> iter;
		if (para != null)
			iter = para.iterator();
		else
			iter = null;
		try {
			// 准备执行语句
			pstmt = conn.prepareStatement(sql);
			// 解析参数列表
			if (iter != null)
				while (iter.hasNext()) {
					Object p = iter.next();
					pstmt.setObject(i++, p);
				}
			// 执行SQL并获得结果集
			ResultSet rs = pstmt.executeQuery();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 利用反射编程，完成自动解析符合下述规范的bean类并加入数据库 对于bean类的规范如下： 1、bean的类名即为表名 2、参数名即为列名
	 * 3、参数值为要输入的值 4、所有为空的参数均不被插入数据库 5、仅支持java标准对象作为成员参数，自定义类请重写toString()方法以便于插入
	 * 6、不支持二进制格式的数据
	 */
	public boolean saveBeans(Object tosave, String table) {

//        String table = tosave.getClass().getSimpleName();//设定：bean名称即为表名称
		Class<? extends Object> ts = tosave.getClass();
		Field[] fields = ts.getDeclaredFields();// 获取类的所有的成员变量
		ArrayList<String> col = new ArrayList<String>();// 列名列表
		ArrayList<Object> values = new ArrayList<Object>();// 参数列表
		try {
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);// 重要！设置权限，否则不能获取参数
				Object val = field.get(tosave);
				if (val == null)// 若值为空，则跳过此属性，即假定为使用数据库中默认值
					continue;
				values.add(val);
				col.add(field.getName());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
		String collist = "";
		// 将列名列表转换为sql语句
		Iterator<String> col_iter = col.iterator();
		while (col_iter.hasNext()) {
			String t = col_iter.next();
			collist += t;
			if (col_iter.hasNext())
				collist += ",";
		}
		// 为参数列表提供相应数量的占位符
		String sit = "";
		for (int i = 0; i < values.size(); i++) {
			sit += "?";
			if (i != values.size() - 1)
				sit += ",";
		}
		// 拼写sql语句
		String sql = "insert into " + table + "(" + collist + ") values (" + sit + ")";
		System.out.println("execute sql is ：" + sql);
		openWriteConnection();
		boolean ret = doSql(sql, values);// 执行sql语句
		closeConnection();
		return ret;
	}

	/**
	 * ！利用Map匹配get/set方法，所以不能有重构的方法（标准情况下也不会有）
	 */
	public ArrayList<Object> getBeans(Class<?> bean, Map<String, Object> index) {

		String table = bean.getSimpleName();// 设定：bean名称即为表名称

		Field[] fields = bean.getDeclaredFields();// 获取类的所有的成员变量
		Method[] methods = bean.getMethods();
		ArrayList<Field> col = new ArrayList<Field>();// 列名列表
		ArrayList<Object> values = new ArrayList<Object>();// 参数列表
		Map<String, Method> methodMap = new HashMap<String, Method>();// 方法列表

		try {
			// 写入列名列表
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);// 重要！设置权限，否则不能获取私有参数
				col.add(field);
			}
			// 写入方法匹配表
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				methodMap.put(method.getName(), method);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
		// 拼写sql语句
		String sql = "select * from " + table;
		if (index != null) {
			// 将限制条件加入sql语句
			String limit = "";
			Iterator<Entry<String, Object>> iter = index.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Object> ent = iter.next();
				limit += ent.getKey() + "=?";
				values.add(ent.getValue());
				if (iter.hasNext())
					limit += " and ";
			}
			sql += " where " + limit;
		}

		System.out.println("execute sql is ：" + sql);
		openWriteConnection();
		ResultSet rs = doQuery(sql, values);// 执行sql语句并获取结果集

		ArrayList<Object> retdata = new ArrayList<Object>();// 创建返回对象的数组
		// 遍历结果集
		try {
			while (rs.next()) {
				Object t = bean.newInstance();// 实例化一个新的bean对象
				Iterator<Field> col_iter = col.iterator();
				while (col_iter.hasNext()) {
					Field f = col_iter.next();
					String colname = f.getName();
					// 设置首字母大写以匹配标准bean方法
					String sub = colname.substring(0, 1);
					colname = colname.substring(1);
					sub = sub.toUpperCase();
					colname = sub + colname;

					Method setter = methodMap.get("set" + colname);// 通过反射获取set方法
					setter.invoke(t, rs.getObject(colname));// 调用set方法插入参数
				}
				retdata.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		closeConnection();
		return retdata;

	}
}
