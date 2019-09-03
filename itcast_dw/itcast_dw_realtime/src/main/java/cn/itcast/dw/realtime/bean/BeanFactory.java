package cn.itcast.dw.realtime.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.itcast.dw.realtime.apps.initialize.DataService;
import cn.itcast.dw.realtime.utils.JsonUtil;

/**
 * 
 * Created by: mengyao
 * 2019年7月2日
 */
public class BeanFactory {

	public static Bean getBean(String record) {
		Bean bean = null;
		if (!StringUtils.isEmpty(record)) {
			JSONObject jsonObj = JsonUtil.json2Obj(record);
			String tableName = jsonObj.getString("tableName");
			Object columnValueList = jsonObj.get("columnValueList");
			Map<String, String> fields = new HashMap<String, String>();
			if (!StringUtils.isEmpty(tableName)) {
				if (columnValueList instanceof JSONArray) {
					JSONArray arr = (JSONArray)columnValueList;
					for (Object obj : arr) {
						if (null!=obj) {
							JSONObject jsonO = (JSONObject)obj;
							fields.put(jsonO.getString("columnName"), jsonO.getString("columnValue"));
						}
					}
				}
				if (columnValueList instanceof Map) {
					fields = (Map<String, String>)columnValueList;
				}
				if (tableName.equals("wst_orders") || tableName.equals("itcast_orders")) {
					if (null != fields) {
						bean = (OrderBean)JsonUtil.map2Obj(fields, OrderBean.class);
					}
				}
				if (tableName.equals("wst_order_goods") || tableName.equals("itcast_order_goods")) {
					if (null != fields) {
						bean = (OrderGoodsBean)JsonUtil.map2Obj(fields, OrderGoodsBean.class);
					}
				}
				if (tableName.equals("wst_logs") || tableName.equals("itcast_logs")) {
					if (null != fields) {
						bean = (LogBean)JsonUtil.map2Obj(fields, LogBean.class);
					}
				}
			}
		}
		return bean;
    }

	public static void main(String[] args) {
		List<String> list = DataService.list();
		String record = list.get(0);
		Bean bean = BeanFactory.getBean(record);
		System.out.println(bean.getClass().getSimpleName());
	}

}
