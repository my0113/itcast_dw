package cn.itcast.dw.realtime.bean;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.itcast.dw.realtime.apps.initialize.DataService;

/**
 * 
 * Created by: mengyao
 * 2019年7月2日
 */
public class BeanFactory {

	public static Bean getBean(String record) {
		Bean bean = null;
		if (record.contains("referer")) {
			bean = JSONObject.parseObject(record, LogBean.class);
		} else
		if (record.contains("orderNo")) {
			bean = JSONObject.parseObject(record, OrderBean.class);
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
