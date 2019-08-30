package cn.itcast.dw.realtime.services.examples;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * 
 * Created by: mengyao
 * 2019年8月2日
 */
public class Test {

	static String appName = Test.class.getSimpleName();
	static final ThreadLocal<SimpleDateFormat> FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
	
	
	public static void main(String[] args) {
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment().setParallelism(4)
				.enableCheckpointing(1000, CheckpointingMode.EXACTLY_ONCE);
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		env.socketTextStream("localhost", 1111).map(record -> {
			String[] fields = record.split(",");
			return Tuple3.of(fields[0], fields[1], Long.parseLong(fields[2]));
		})
		.returns(Types.TUPLE(Types.STRING, Types.STRING, Types.LONG))
		.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Tuple3<String, String, Long>>() {
			private static final long serialVersionUID = -8134477006009350631L;
			long currentMaxTimestamp = 0L;
			long maxOutOfOrderness = 5 * 1000L;// 最大允许的乱序时间是5s
			@Override
			public long extractTimestamp(Tuple3<String, String, Long> element, long previousElementTimestamp) {return currentMaxTimestamp = element.f2;}
			@Override
			public Watermark getCurrentWatermark() {return new Watermark(currentMaxTimestamp - maxOutOfOrderness);}
		})
		.windowAll(TumblingEventTimeWindows.of(Time.seconds(1)))
		.aggregate(new AggregateFunction<Tuple3<String, String, Long>, Visit, Visit>() {
			private static final long serialVersionUID = -1232594830767719510L;
			@Override
            public Visit createAccumulator() {return new Visit();}
            @Override
            public Visit add(Tuple3<String, String, Long> t3, Visit visitor) {return visitor.add(t3);}
            @Override
            public Visit getResult(Visit visitor) {return visitor;}
            @Override
            public Visit merge(Visit curVisitor, Visit otherVisitor) {return curVisitor.merge(otherVisitor);}
        }, new ProcessAllWindowFunction<Visit, Tuple2<String, String>, TimeWindow>() {
			private static final long serialVersionUID = 8594209983471134147L;
			@Override
            public void process(Context context, Iterable<Visit> in, Collector<Tuple2<String, String>> out) throws Exception {
				SimpleDateFormat sdf = FORMAT.get();
            	Iterator<Visit> iterator = in.iterator();
            	if (iterator.hasNext()) {
            		Visit visitor = iterator.next();
            		//Tuple2<String, String> result = Tuple2.of("quota_visitor", visitor.toString());
					//out.collect(result);
            		System.out.println(sdf.format(new Date(context.window().getStart())) + " ~ " + sdf.format(new Date(context.window().getEnd())) + ": " + visitor);
				}
            }
        })
		.print()
		.setParallelism(1)
		;

	}

	static class Visit implements Serializable {
		private static final long serialVersionUID = 1L;
		private Integer pv;
		private HashSet<String> uv;
		public Visit() {
			this.pv = 0;
			this.uv = new HashSet<>();
		}
		public Visit add(Tuple3<String, String, Long> e) {
			this.pv += 1;
			this.uv.add(e.f1);
			return this;
		}
		public Visit merge(Visit other) {
			this.uv.addAll(other.uv);
			this.pv += other.pv;
			return this;
		}
		public Integer getPv() {
			return pv;
		}
		public void setPv(Integer pv) {
			this.pv = pv;
		}
		public HashSet<String> getUv() {
			return uv;
		}
		public void setUv(HashSet<String> uv) {
			this.uv = uv;
		}
		@Override
		public String toString() {return "{\"pv\":"+pv+",\"uv\":"+uv.size()+"}";}
	}

}
