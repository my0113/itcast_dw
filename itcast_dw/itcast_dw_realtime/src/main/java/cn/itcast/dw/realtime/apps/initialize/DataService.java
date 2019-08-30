package cn.itcast.dw.realtime.apps.initialize;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

import cn.itcast.dw.realtime.bean.LogBean;
import cn.itcast.dw.realtime.bean.OrderBean;

/**
 * 测试数据
 * Created by: mengyao
 * 2019年7月29日
 */
public class DataService {

	public static List<String> getTestLog10(){
		//String guid,long count,String trackTime,String url,String referer,String orderCode
		return Arrays.asList(
				JSON.toJSONString(new LogBean("1",1L, "2019-07-10 14:42:21","jiadian.jd.com","www.jd.com","13120113")),
				JSON.toJSONString(new LogBean("1",1L, "2019-07-10 14:42:21","jiadian.jd.com","www.jd.com","13120113")),
				JSON.toJSONString(new LogBean("1",1L, "2019-07-10 14:42:21","jiadian.jd.com","www.jd.com","13120113")),
				JSON.toJSONString(new LogBean("2",1L, "2019-07-10 14:42:21","jiadian.jd.com","www.jd.com","13120113")),
				JSON.toJSONString(new LogBean("2",1L, "2019-07-10 14:42:21","jiadian.jd.com","www.jd.com","13120113")),
				JSON.toJSONString(new LogBean("2",1L, "2019-07-10 14:42:21","jiadian.jd.com","www.jd.com","13120113")),
				JSON.toJSONString(new LogBean("3",1L, "2019-07-10 14:42:21","jiadian.jd.com","www.jd.com","13120113")),
				JSON.toJSONString(new LogBean("3",1L, "2019-07-10 14:42:21","jiadian.jd.com","www.jd.com","13120113")),
				JSON.toJSONString(new LogBean("3",1L, "2019-07-10 14:42:21","jiadian.jd.com","www.jd.com","13120113")),
				JSON.toJSONString(new LogBean("3",1L, "2019-07-10 14:42:21","jiadian.jd.com","www.jd.com","13120113"))
				);
	}
	
	public static List<String> list(){
		List<String> logs = getLogs();
		List<String> orders = getOrders();
		logs.addAll(orders);
		return logs;
	}
	
	private static List<String> getLogs(){
		List<String> logs = new ArrayList<String>();
		try {
			List<String> list = FileUtils.readLines(new File("D:\\works\\ITCAST\\项目\\实时数仓项目\\logs-test.csv"));
			list.forEach(line->{
				String[] fieldStrs = line.split(",",36);
				long id = Long.valueOf(fieldStrs[0]);
				String url = fieldStrs[1];
				String referer = fieldStrs[2];
				String keyword = fieldStrs[3];
				String type = fieldStrs[4];
				String guid = fieldStrs[5];
				String pageId = fieldStrs[6];
				String moduleId = fieldStrs[7];
				String linkId = fieldStrs[8];
				String attachedInfo = fieldStrs[9];
				String sessionId = fieldStrs[10];
				String trackerU = fieldStrs[11];
				String trackerType = fieldStrs[12];
				String ip = fieldStrs[13];
				String trackerSrc = fieldStrs[14];
				String cookie = fieldStrs[15];
				String orderCode = fieldStrs[16];
				String trackTime = fieldStrs[17];
				String endUserId = fieldStrs[18];
				String firstLink = fieldStrs[19];
				String sessionViewNo = fieldStrs[20];
				String productId = fieldStrs[21];
				String curMerchantId = fieldStrs[22];
				String provinceId = fieldStrs[23];
				String cityId = fieldStrs[24];
				String fee = fieldStrs[25];
				String edmActivity = fieldStrs[26];
				String edmEmail = fieldStrs[27];
				String edmJobId = fieldStrs[28];
				String ieVersion = fieldStrs[29];
				String platform = fieldStrs[30];
				String internalKeyword = fieldStrs[31];
				String resultSum = fieldStrs[32];
				String currentPage = fieldStrs[33];
				String linkPosition = fieldStrs[34];
				String buttonPosition = fieldStrs[35];
				LogBean bean = new LogBean(id, url, referer, keyword, type, guid, pageId, moduleId, linkId, attachedInfo, sessionId, trackerU, trackerType, ip, trackerSrc, cookie, orderCode, trackTime, endUserId, firstLink, sessionViewNo, productId, curMerchantId, provinceId, cityId, fee, edmActivity, edmEmail, edmJobId, ieVersion, platform, internalKeyword, resultSum, currentPage, linkPosition, buttonPosition);
				logs.add(JSON.toJSONString(bean));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logs;
	}
	
	private static List<String> getOrders() {
		List<String> list = new ArrayList<String>();
		list.add(JSON.toJSONString(new OrderBean(1,"1000000",2, 6,-1,509.52,1,0.00 ,509.52,499.33,0,"1",0,37,"13_42_81","lisi","shanghai","157", 2,0,"(NULL)","周末不送货",0,10.50,0,0,0,0,0,0,"(NULL)",0,"节日礼品","7c439a1c-3e88-4c65-bd82-cd6813df0ed7","2019-06-27 01:28:15","2019-06-24 01:28:15","4ffcb13d-9510-4461-9e43-2b997af4637d",1,"2019-06-22 17:28:15",0,4.99,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(2,"5000000",4,72,-1,503.86,1,0.00 ,503.86,503.86,1,"1",0,36,"5_42_38 ","zhangsan","shanghai","150", 5,0,"(NULL)","加急，加急",0,12.50,0,0,0,0,0,0,"(NULL)",0,"养生    ","d6b07c75-01ee-42a4-b0c4-59abb9b4cef6","2019-06-27 01:28:15","2019-06-24 01:28:15","b86ef520-6196-4dd2-8847-0d5ca769ce92",1,"2019-06-22 17:28:15",3,5.04,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(3,"8000000",4,42,-3,500.60,0,10.01,510.61,510.61,0,"1",0,40,"6_81_83 ","zhaoliu ","beijing ","189", 9,0,"(NULL)","周末不送货",1,10.50,0,0,0,0,0,0,"(NULL)",0,"美白    ","0cf14485-831d-449d-98c8-3ac41a6df92d","2019-06-27 01:28:15","2019-06-24 01:28:15","5ef59e41-0c83-4d95-992c-9727922c6303",1,"2019-06-22 17:28:15",5,5.11,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(4,"3000000",0,86, 0,501.89,0,10.04,511.93,511.93,1,"1",0,80,"4_37_83 ","zhangsan","tianjin ","157", 8,0,"(NULL)","周末不送货",0,12.50,0,0,0,0,0,0,"(NULL)",0,"养生    ","842bcd3e-0142-421d-926d-8020cf016fa2","2019-06-27 01:28:15","2019-06-24 01:28:15","b79c3cec-6a16-40aa-a5ae-f3dba2b606f7",1,"2019-06-22 17:28:15",4,5.12,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(5,"1000000",1,22, 0,501.02,1,0.00 ,501.02,491.00,0,"2",0,82,"8_42_81 ","zhaoliu ","tianjin ","189", 3,0,"(NULL)","加急，加急",4,12.50,0,0,0,0,0,0,"(NULL)",0,"养生    ","b8b77c96-7f5e-4e2e-835c-802812cc0dc7","2019-06-27 01:28:15","2019-06-24 01:28:15","39260684-43df-45e8-b6c8-6b2699767566",1,"2019-06-22 17:28:15",4,4.91,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(6,"6000000",4,48,-2,501.31,1,0.00 ,501.31,441.15,1,"1",0,80,"3_80_38 ","zhaoliu ","tianjin ","157", 9,0,"(NULL)","周末不送货",0,10.50,0,0,0,0,0,0,"(NULL)",0,"养生    ","33a28938-66b5-4473-a952-c91482ad49ee","2019-06-27 01:28:15","2019-06-24 01:28:15","378a616a-2bfb-4419-945e-832ca5c9f497",1,"2019-06-22 17:28:15",4,4.41,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(7,"8000000",4,38, 0,507.62,1,0.00 ,507.62,497.47,0,"1",0,83,"8_38_37 ","wangwu  ","shanghai","138", 6,0,"(NULL)","周末不送货",2,12.50,0,0,0,0,0,0,"(NULL)",0,"养生    ","1c926373-a474-4520-a0ef-895deab91b74","2019-06-27 01:28:15","2019-06-24 01:28:15","a260ce57-8bc3-44f6-a9e9-01ed4bd8786e",1,"2019-06-22 17:28:15",3,4.97,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(8,"8000000",0,14, 1,504.31,1,0.00 ,504.31,504.31,1,"2",0,38,"2_39_41 ","lisi    ","tianjin ","157", 5,0,"(NULL)","加急，加急",0,12.50,0,0,0,0,0,0,"(NULL)",0,"节日礼品","cf067de9-ace1-43f8-875d-910c7a82c3b8","2019-06-27 01:28:15","2019-06-24 01:28:15","876f89b1-655a-4f05-92b4-3de12f653b87",1,"2019-06-22 17:28:15",4,5.04,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(9,"1000000",1,86, 0,502.52,1,0.00 ,502.52,442.22,0,"2",0,37,"7_82_41 ","lisi    ","shanghai","150", 9,0,"(NULL)","加急，加急",4,12.50,0,0,0,0,0,0,"(NULL)",0,"美白    ","45600c80-1f86-4e69-af5f-2bcdea6cabc7","2019-06-27 01:28:15","2019-06-24 01:28:15","315895e2-72fa-4797-b562-33f53688ed05",1,"2019-06-22 17:28:15",0,4.42,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(520,"8000000",6,90,-2,507.63,1,0.00 ,507.63,446.71,1,"1",0,40,"7_40_39 ","zhaoliu ","shanghai","150", 4,0,"(NULL)","加急，加急",2,10.50,0,0,0,0,0,0,"(NULL)",0,"防晒    ","6fa90d8f-e8c5-4dc8-bfd6-99acd206a5cd","2019-06-26 19:11:34","2019-06-23 19:11:34","9421d563-e0fe-45a2-aaad-6e7b71bbef3f",1,"2019-06-22 11:11:34",1,4.47,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(521,"2000000",5,10, 1,505.41,1,0.00 ,505.41,444.76,0,"2",0,38,"4_36_80 ","wangwu  ","beijing ","150",10,0,"(NULL)","加急，加急",1,10.50,0,0,0,0,0,0,"(NULL)",0,"防晒    ","e06e99f8-efb3-4c28-9d09-811bfa86b7b8","2019-06-26 19:11:34","2019-06-23 19:11:34","a641c29b-dfdd-48e3-8169-0cb851775f2c",1,"2019-06-22 11:11:34",1,4.45,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(522,"3000000",5,28, 2,507.86,0,10.16,518.01,455.85,1,"2",0,36,"7_83_36 ","wangwu  ","shanghai","189", 6,0,"(NULL)","加急，加急",3,10.50,0,0,1,0,0,0,"(NULL)",0,"节日礼品","e2588d67-6e99-4fad-bb7c-a3600c4e282b","2019-06-26 19:11:34","2019-06-23 19:11:34","9be1434c-a238-4d54-a214-49162c75b8bd",1,"2019-06-22 11:11:34",1,4.56,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(523,"2000000",3,34,-3,506.40,1,0.00 ,506.40,445.63,0,"2",0,39,"9_36_41 ","wangwu  ","beijing ","138", 9,0,"(NULL)","周末不送货",1,12.50,0,0,0,0,0,0,"(NULL)",0,"节日礼品","ae28baeb-89fe-456e-862c-0fc1246fe51c","2019-06-26 19:11:34","2019-06-23 19:11:34","de264918-a2f1-427c-a97f-fbb03dbf9fa1",1,"2019-06-22 11:11:34",5,4.46,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(524,"4000000",2, 4,-1,503.04,0,10.06,513.10,502.84,1,"2",0,83,"12_80_41","zhaoliu ","beijing ","157", 5,0,"(NULL)","加急，加急",3,10.50,0,0,0,0,0,0,"(NULL)",0,"美白    ","28f5ddd9-127b-4b3c-a0a8-89f5d11dfe09","2019-06-26 19:11:34","2019-06-23 19:11:34","e0e2e879-03aa-4020-976c-f46323b402f9",1,"2019-06-22 11:11:34",3,5.03,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(525,"7000000",6,56,-2,509.06,1,0.00 ,509.06,509.06,0,"1",0,42,"11_37_38","wangwu  ","tianjin ","189", 3,0,"(NULL)","周末不送货",0,10.50,0,0,0,0,0,0,"(NULL)",0,"美白    ","34e17f37-79e1-4b08-a4ef-1a8fb34c2ce5","2019-06-26 19:11:34","2019-06-23 19:11:34","6c1c459e-c528-4268-a82d-e8ae1a36e2cf",1,"2019-06-22 11:11:34",5,5.09,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(526,"1000000",0, 8,-1,508.85,0,10.18,519.03,519.03,1,"1",0,37,"10_83_83","lisi    ","tianjin ","150", 5,0,"(NULL)","加急，加急",0,12.50,0,0,0,0,0,0,"(NULL)",0,"防晒    ","c98bb662-e1e3-4388-8bc0-bff8a99450f3","2019-06-26 19:11:34","2019-06-23 19:11:34","db87987f-b44e-4e22-b64e-e38e2a5a4575",1,"2019-06-22 11:11:34",1,5.19,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(527,"6000000",3,44,-3,505.54,0,10.11,515.65,453.77,0,"1",0,82,"10_83_38","wangwu  ","shanghai","189",10,0,"(NULL)","周末不送货",0,12.50,0,0,0,0,0,0,"(NULL)",0,"节日礼品","1859c54d-b910-41af-bf4c-3f608ecdda4d","2019-06-26 19:11:34","2019-06-23 19:11:34","80d8233f-c0c2-4d0a-8230-a76299441d14",1,"2019-06-22 11:11:34",4,4.54,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(528,"1000000",2,92, 1,502.58,1,0.00 ,502.58,442.27,1,"2",0,81,"7_42_36 ","zhaoliu ","shanghai","157", 7,0,"(NULL)","周末不送货",4,10.50,0,0,0,0,0,0,"(NULL)",0,"养生    ","f347de24-22d3-4209-8359-62ff2ca4d6b7","2019-06-26 19:11:34","2019-06-23 19:11:34","f5a27ec2-7e18-44a9-b190-1cb25e1f4332",1,"2019-06-22 11:11:34",1,4.42,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(529,"6000000",0,98, 1,505.19,0,10.10,515.30,504.99,0,"1",0,38,"13_81_41","zhaoliu ","shanghai","189", 5,0,"(NULL)","周末不送货",4,12.50,0,0,0,0,0,0,"(NULL)",0,"节日礼品","af3259ef-dd6f-4bf6-9f7e-c9bc1732e41f","2019-06-26 19:11:34","2019-06-23 19:11:34","9780e4da-5e36-45e2-9ecd-24f96fe63009",1,"2019-06-22 11:11:34",2,5.05,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		list.add(JSON.toJSONString(new OrderBean(530,"1000000",5,74, 2,502.64,1,0.00 ,502.64,442.32,1,"2",0,83,"6_37_38 ","zhaoliu ","tianjin ","157", 1,0,"(NULL)","周末不送货",1,10.50,0,0,1,1,0,0,"(NULL)",0,"节日礼品","5cd59005-1aa4-473b-9b59-1847ed4a7ceb","2019-06-27 01:28:15","2019-06-24 01:28:15","ec8912f6-357f-4e11-bdb6-bd1ece5eeacd",1,"2019-06-22 17:28:15",5,4.42,0.00,0,"order","(NULL)",0,0,"(NULL)",0.00,"(NULL)",0,0)));
		return list;
	}
	
	public static void main(String[] args) {
		Queue<String> queue = new LinkedList<String>(DataService.list().subList(0, 40));
		DataService.getLogs().subList(0, 40).forEach(line -> System.out.println(line));
	}

	
	private static void getInsertSQL(String path, String fieldSeperator, int fieldNum, String targetPath) {
		try {
			List<String> sqls = new ArrayList<String>(); 
			List<String> lines = FileUtils.readLines(new File(path), "UTF-8");
			lines.forEach(line->{
				StringBuilder builder = new StringBuilder("insert into wst_logs(`id`,`url`,`referer`,`keyword`,`type`,`guid`,`page_id`,`module_id`,`link_id`,`attached_info`,`session_id`,`tracker_u`,`tracker_type`,`ip`,`tracker_src`,`cookie`,`order_code`,`track_time`,`end_user_id`,`first_link`,`session_view_no`,`product_id`,`cur_merchant_id`,`province_id`,`city_id`,`fee`,`edm_activity`,`edm_email`,`edm_job_id`,`ie_version`,`platform`,`internal_keyword`,`result_sum`,`current_page`,`link_position`,`button_position`) values(");
				String[] fields = line.split(fieldSeperator);
				for (int i = 0; i < fieldNum; i++) {
					builder.append("'"+fields[i]+"',");
				}
				builder.setLength(builder.toString().length()-1);
				builder.append(");");
				sqls.add(builder.toString());
				builder.setLength(0);
			});
			//sqls.forEach(System.out::println);
			FileUtils.writeLines(new File(targetPath), sqls, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void getInsertSQL(String sqlFront, String path, String fieldSeperator, int fieldNum, String targetPath) {
		try {
			List<String> sqls = new ArrayList<String>();
			List<String> lines = FileUtils.readLines(new File(path), "UTF-8");
			lines.forEach(line->{
				StringBuilder builder = new StringBuilder(sqlFront);
				String[] fields = line.split(fieldSeperator);
				for (int i = 0; i < fieldNum; i++) {
					builder.append("'"+fields[i]+"',");
				}
				builder.setLength(builder.toString().length()-1);
				builder.append(");");
				sqls.add(builder.toString());
				builder.setLength(0);
			});
			sqls.forEach(System.out::println);
			FileUtils.writeLines(new File(targetPath), sqls, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
