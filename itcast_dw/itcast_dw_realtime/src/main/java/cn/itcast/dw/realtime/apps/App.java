package cn.itcast.dw.realtime.apps;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousEventTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.apache.flink.util.Collector;
import org.apache.flink.util.ExecutorUtils;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import cn.itcast.dw.realtime.bean.Bean;
import cn.itcast.dw.realtime.bean.BeanFactory;
import cn.itcast.dw.realtime.bean.LogBean;
import cn.itcast.dw.realtime.bean.OrderBean;
import cn.itcast.dw.realtime.bean.OrderDetailBean;
import cn.itcast.dw.realtime.bean.OrderGoodsBean;
import cn.itcast.dw.realtime.utils.JsonUtil;
import cn.itcast.dw.realtime.utils.RedisUtil;

/**
 * 
 * 实时数仓主程序
 * Created by: mengyao
 * 2019年7月16日
 */
public class App {

	static String appName = App.class.getSimpleName();
	static final ThreadLocal<SimpleDateFormat> FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
	static final String INPUT_TOPIC = "test";
	static final String OUTPUT_TOPIC = "detail";
	static final String ZK_ADDR = "bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181";
	static final String KAFKA_ADDR = "bigdata-cdh01:9092,bigdata-cdh02:9092,bigdata-cdh03:9092";
	static final String REDIS_ADDR = "bigdata-cdh01";
	static final int REDIS_PORT = 6379;
	static final String HBASE_TABLE = "dwd_order_detail";
	static final String HBASE_TABLE_FAMILY = "detail";
	static final RedisSinkMapper redisSinkMapper = new RedisSinkMapper();
	

	public static void main(String[] args) throws Exception {
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment()
				.setParallelism(4)
				.enableCheckpointing(1000, CheckpointingMode.EXACTLY_ONCE);
				// .setStateBackend(new FsStateBackend("hdfs://bigdata-cdh01:9000/dashboard/checkpoint/"));
		env.setRestartStrategy(RestartStrategies.fixedDelayRestart(1, 1000));
		// 设置时间类型
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		// 设置两次checkpoint的最小时间间隔
		env.getCheckpointConfig().setMinPauseBetweenCheckpoints(1000);
		// checkpoint超时的时长
		env.getCheckpointConfig().setCheckpointTimeout(60000);
		// 允许的最大checkpoint并行度
		env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
		// 当程序关闭的时，触发额外的checkpoint
		env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

		@SuppressWarnings("serial")
		Properties props = new Properties() {{
			setProperty("bootstrap.servers", KAFKA_ADDR);
			setProperty("zookeeper.connect", ZK_ADDR);
			setProperty("group.id", "test-01");
		}};

		//读取kafka主题中的订单、订单商品、订单商品评价、订单商品退款数据
		FlinkKafkaConsumer011<String> kafkaSource = new FlinkKafkaConsumer011<String>(INPUT_TOPIC,
				new SimpleStringSchema(), props);
		
		//实例化为AbstractBean
		DataStream<Bean> inputSS = env.addSource(kafkaSource)
			.map(record -> BeanFactory.getBean(record))
			.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Bean>() {
				private static final long serialVersionUID = -8134477006009350631L;
				long currentMaxTimestamp = 0L;
                long maxOutOfOrderness = 10 * 1000L;//最大允许的乱序时间是10s
                @Override
                public long extractTimestamp(Bean element, long previousElementTimestamp) {return currentMaxTimestamp = element.getTs();}
                @Override
                public Watermark getCurrentWatermark() {return new Watermark(currentMaxTimestamp - maxOutOfOrderness);}
            });
		
		//读取Redis中的商品维表
		DataStreamSource<OrderGoodsBean> orderGoodsDS = env.addSource(new SourceFunction<OrderGoodsBean>() {
				private static final long serialVersionUID = 3089297720642290688L;
				private volatile boolean isRunning = true;
				private RedisUtil redis = RedisUtil.build(REDIS_ADDR, REDIS_PORT);
				private String ORDER_GOODS_KEY_PATTERN = "dim_og_*";//订单商品维表的key前缀
				@Override
				public void run(SourceContext<OrderGoodsBean> ctx) throws Exception {
					while (isRunning) {
						Thread.sleep(10*1000);
						Set<String> keys = redis.getKeys(ORDER_GOODS_KEY_PATTERN);
						List<String> orderGoodsJsons = redis.getValues(keys);
						//System.out.println("==== get: "+orderGoodsJsons);
						if (null != orderGoodsJsons) {
							orderGoodsJsons.forEach(v -> {
								if(JsonUtil.isJSONValid(v)) {
									Object obj = JsonUtil.json2Obj(v, OrderGoodsBean.class);
									if (obj instanceof OrderGoodsBean) {
										OrderGoodsBean odBean = (OrderGoodsBean)obj;
										if (odBean.getOrderId() > 0) {
											//ctx.collect(odBean);
										}
									}
								}
							});
						}
					}
				}
				@Override
				public void cancel() {isRunning = false;}
			})
			.setParallelism(1);
		
		//获取订单
		DataStream<OrderBean> orderDS = inputSS
			.filter(bean -> bean instanceof OrderBean)
			.map(bean -> (OrderBean)bean);
		
		//订单关联商品
		DataStream<OrderDetailBean> detailDS = orderDS
			.connect(orderGoodsDS)
			.map(new CoMapFunction<OrderBean, OrderGoodsBean, OrderDetailBean>() {
				private static final long serialVersionUID = 4822070855575490306L;
				OrderDetailBean bean = new OrderDetailBean();
				@Override
				public OrderDetailBean map1(OrderBean value) throws Exception {
					bean.add(value);
					return bean;
				}
				@Override
				public OrderDetailBean map2(OrderGoodsBean value) throws Exception {bean.add(value);return bean;}
			});
		// 保存到hbase
		detailDS.addSink(new HBaseSink(HBASE_TABLE, HBASE_TABLE_FAMILY));
		// 提供Druid的数据源
		//detailDS.addSink(new KafkaSink());
		
		//获取行为数据
		DataStream<LogBean> logDS = inputSS
			.filter(bean -> bean instanceof LogBean)
			.map(bean -> (LogBean)bean);
		
		//转化率指标的cep模式定义：p1浏览、p2加购物车、p3下单、p4付款
		//转化率指标的cep模式流：监控用户的连续事件,假设App端、PC端最大连续使用时间为3分钟
		//符合转化率指标模式流的行为
		
        Pattern<LogBean, LogBean> convertRatePattern = Pattern.<LogBean>
	        begin("p1")
	        .where(new IterativeCondition<LogBean>() {
				private static final long serialVersionUID = -4173228553430455129L;
				@Override
	            public boolean filter(LogBean log, Context<LogBean> context) throws Exception {
	                return log.getUrl().startsWith("http://item");
	            }
	        })
	        .next("p2")
	        .where(new IterativeCondition<LogBean>() {
				private static final long serialVersionUID = -5300045631734059157L;
				@Override
	            public boolean filter(LogBean log, Context<LogBean> context) throws Exception {
	                return log.getUrl().startsWith("http://cart");
	            }
	        })
	        .next("p3")
	        .where(new IterativeCondition<LogBean>() {
				private static final long serialVersionUID = -8278259712570067814L;
				@Override
	            public boolean filter(LogBean log, Context<LogBean> context) throws Exception {
	                return log.getUrl().startsWith("http://order");
	            }
	        })
	        .next("p4")
	        .where(new IterativeCondition<LogBean>() {
				private static final long serialVersionUID = -3151436526492966858L;
				@Override
	            public boolean filter(LogBean log, Context<LogBean> context) throws Exception {
	                return log.getUrl().startsWith("http://buy");
	            }
	        })
	        .within(Time.seconds(3));

        PatternStream<LogBean> logPS = CEP.pattern(
    		logDS.keyBy(LogBean::getGuid),
    		convertRatePattern);

		logPS.select(new PatternSelectFunction<LogBean, Tuple2<String, String>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Tuple2<String, String> select(Map<String, List<LogBean>> pattern) throws Exception {
				int browseNumber = pattern.get("p1").size();//浏览人数
	        	int cartNumber = pattern.get("p2").size();//加购物车人数
	        	int orderNumber = pattern.get("p3").size();//下单人数
	        	int payNumber = pattern.get("p4").size();//付款人数
	        	LogBean p1 = pattern.get("p1").get(0);
	        	LogBean p2 = pattern.get("p2").get(0);
	        	LogBean p3 = pattern.get("p3").get(0);
	        	LogBean p4 = pattern.get("p4").get(0);
	            String res = p1.getGuid()+"["+p1.getIp()+", "+p1.getUrl()+"]\n"+
		            		 p2.getGuid()+"["+p2.getIp()+", "+p2.getUrl()+"]\n"+
		            		 p3.getGuid()+"["+p3.getIp()+", "+p3.getUrl()+"]\n"+
		            		 p4.getGuid()+"["+p4.getIp()+", "+p4.getUrl()+"]";
	            System.out.println("#### "+res);
	            return Tuple2.of("quora_convert", new ConvertRate(browseNumber, cartNumber, orderNumber, payNumber).toString());
			}
		})
//		.select((Map<String, List<LogBean>> pattern) -> {
//	    		int browseNumber = pattern.get("p1").size();//浏览人数
//	        	int cartNumber = pattern.get("p2").size();//加购物车人数
//	        	int orderNumber = pattern.get("p3").size();//下单人数
//	        	int payNumber = pattern.get("p4").size();//付款人数
//	        	LogBean p1 = pattern.get("p1").get(0);
//	        	LogBean p2 = pattern.get("p2").get(0);
//	        	LogBean p3 = pattern.get("p3").get(0);
//	        	LogBean p4 = pattern.get("p4").get(0);
//	            String res = p1.getGuid()+"["+p1.getIp()+", "+p1.getUrl()+"]\n"+
//		            		 p2.getGuid()+"["+p2.getIp()+", "+p2.getUrl()+"]\n"+
//		            		 p3.getGuid()+"["+p3.getIp()+", "+p3.getUrl()+"]\n"+
//		            		 p4.getGuid()+"["+p4.getIp()+", "+p4.getUrl()+"]";
//	            System.out.println("#### "+res);
//	            return Tuple2.of("quora_convert", new ConvertRate(browseNumber, cartNumber, orderNumber, payNumber).toString());
//	        })
			.returns(Types.TUPLE(Types.STRING, Types.STRING))
	        .addSink(new RedisSink<Tuple2<String, String>>(new FlinkJedisPoolConfig.Builder().setHost(REDIS_ADDR).setPort(REDIS_PORT).build(), redisSinkMapper))
	        .setParallelism(1);
		
		//pv,uv,ip指标
		logDS
			.windowAll(TumblingEventTimeWindows.of(Time.days(1), Time.hours(-8)))
//			.windowAll(TumblingProcessingTimeWindows.of(Time.days(1), Time.hours(-8)))
	        //.trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(60))
	        //.trigger(ContinuousEventTimeTrigger.of(Time.seconds(1)))
	        .trigger(CountTrigger.of(1))
	        .aggregate(new AggregateFunction<LogBean, Visitor, Visitor>() {
					private static final long serialVersionUID = 6138840650014636996L;
					@Override
		            public Visitor createAccumulator() {return new Visitor();}
		            @Override
		            public Visitor add(LogBean logBean, Visitor visitor) {return visitor.add(logBean);}
		            @Override
		            public Visitor getResult(Visitor visitor) {return visitor;}
		            @Override
		            public Visitor merge(Visitor curVisitor, Visitor otherVisitor) {return curVisitor.merge(otherVisitor);}
		        }, new ProcessAllWindowFunction<Visitor, Tuple2<String, String>, TimeWindow>() {
					private static final long serialVersionUID = 4731349509708155906L;
					@Override
		            public void process(Context context, Iterable<Visitor> in, Collector<Tuple2<String, String>> out) throws Exception {
		            	Iterator<Visitor> iterator = in.iterator();
		            	if (iterator.hasNext()) {
		            		Visitor visitor = iterator.next();
							//SimpleDateFormat sdf = FORMAT.get();
							//System.out.println(sdf.format(new Date(context.window().getStart())) + " ~ " + sdf.format(new Date(context.window().getEnd())) + ": " + visitor);
							String string = visitor.toString();
							System.out.println("==== "+string);
							out.collect(Tuple2.of("quota_visitor", visitor.toString()));
						}
		            }
		    })
	        .addSink(new RedisSink<Tuple2<String, String>>(new FlinkJedisPoolConfig.Builder().setHost(REDIS_ADDR).setPort(REDIS_PORT).build(), redisSinkMapper))
	        .setParallelism(1);
		
		//懒加载执行
        env.execute(appName);
	}
	//访客
	static class Visitor {
		private int pv;
        private HashSet<String> uv;
        private HashSet<String> ip;
        public Visitor() {
            this.pv = 0;
            this.uv = new HashSet<String>();
            this.ip = new HashSet<String>();
        }
        public Visitor add(LogBean log) {
            this.pv += 1;
            this.uv.add(log.getGuid());
            this.ip.add(log.getIp());
            return this;
        }
        public Visitor merge(Visitor other) {
        	this.pv += other.pv;
            this.uv.addAll(other.uv);
            this.ip.addAll(other.ip);
            return this;
        }
        @Override
        public String toString() {return "{\"pv\":"+pv+",\"uv\":"+uv.size()+",\"ip\":"+ip.size()+"}";}
	}
	//转化率
	static class ConvertRate {
		private long bn;//browseNumber
		private long cn;//cartNumber
		private long on;//orderNumber
		private long pn;//payNumber
		public long getBn() {
			return bn;
		}
		public void setBn(long bn) {
			this.bn = bn;
		}
		public long getCn() {
			return cn;
		}
		public void setCn(long cn) {
			this.cn = cn;
		}
		public long getOn() {
			return on;
		}
		public void setOn(long on) {
			this.on = on;
		}
		public long getPn() {
			return pn;
		}
		public void setPn(long pn) {
			this.pn = pn;
		}
		public ConvertRate(long bn, long cn, long on, long pn) {
			this.bn = bn;
			this.cn = cn;
			this.on = on;
			this.pn = pn;
		}
		@Override
		public String toString() {
			return "{\"bn\":" + bn + ", cn\":" + cn + ", on\":" + on + ", pn\":" + pn + "}";
		}		
	}
	
	static final class RedisAsyncDIM extends RichAsyncFunction<OrderBean, OrderDetailBean> {
		private static final long serialVersionUID = 6915318243822834435L;
		private transient ExecutorService pool;
		@Override
		public void open(Configuration conf) throws Exception {
			pool = Executors.newFixedThreadPool(conf.getInteger("async.executor.pool.num", 2));
		}
		@Override
		public void asyncInvoke(OrderBean input, ResultFuture<OrderDetailBean> resultFuture) throws Exception {
			pool.submit(() -> resultFuture.complete(Collections.singletonList(null)));
		}
		@Override
		public void close() throws Exception {
			ExecutorUtils.gracefulShutdown(60000L, TimeUnit.MILLISECONDS, pool);
		}
	}
	
	static final class HBaseSink extends RichSinkFunction<OrderDetailBean> {
		private static final long serialVersionUID = 4534629191235393968L;
		private String tableName,family;
		public HBaseSink(String tableName, String family) {
			this.tableName = tableName;
			this.family = family;
		}
		private Connection connection;
		@Override
		public void open(Configuration conf) throws Exception {
			org.apache.hadoop.conf.Configuration hadoopConf = HBaseConfiguration.create();
			hadoopConf.set("hbase.zookeeper.quorum", ZK_ADDR);
			connection = ConnectionFactory.createConnection(hadoopConf);
		}
		@Override
		public void invoke(OrderDetailBean bean) throws Exception {
			Table table = connection.getTable(TableName.valueOf(tableName));
			Put record = new Put(Bytes.toBytes(bean.getOrderNo()));
			JsonUtil.obj2Map(bean).forEach((k,v)->{
				record.addColumn(Bytes.toBytes(family), Bytes.toBytes(k), Bytes.toBytes(v.toString()));
			});
			table.put(record);
			table.close();
		}
		@Override
		public void close() throws Exception {
			super.close();
			if(!connection.isClosed()) {connection.close();}
		}
	}
	
	static class RedisSinkMapper implements RedisMapper<Tuple2<String, String>>{
		private static final long serialVersionUID = 1257606386583452589L;
		public RedisCommandDescription getCommandDescription() {return new RedisCommandDescription(RedisCommand.SET);}
	    public String getKeyFromData(Tuple2<String, String> data) {return data.f0;}
	    public String getValueFromData(Tuple2<String, String> data) {return data.f1;}
	}

}
