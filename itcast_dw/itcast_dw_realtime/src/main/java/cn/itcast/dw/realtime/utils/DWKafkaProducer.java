package cn.itcast.dw.realtime.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import cn.itcast.dw.realtime.apps.initialize.DataService;

/**
 * 
 * @author mengyao
 *
 */
public class DWKafkaProducer {

	private String topic;
	private Properties props;

	private DWKafkaProducer(String topic, Properties props) {
		this.topic = topic;
		this.props = props;
	}

	/**
	 * 创建生产者实例
	 * @param topic
	 * @param props
	 * @return
	 */
	public static DWKafkaProducer create(String topic, Properties props) {
		return new DWKafkaProducer(topic, props);
	}

	/**
	 * 异步生产消息
	 * @param msgs
	 */
	public void asycnSenders(Collection<String> msgs) {
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		Queue<String> queue = new LinkedList<String>(msgs);
		try {
			while (queue.size() > 0) {
				producer.send(new ProducerRecord<String, String>(topic, queue.poll()), new Callback() {
					public void onCompletion(RecordMetadata metadata, Exception e) {
						if (e != null) {
							e.printStackTrace();
						} else {
							System.out.println(": " + metadata.offset());
						}
					}
				});
			}
			producer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}

	/**
	 * 同步生产消息
	 * @param msgs
	 */
	public void senders(Collection<String> msgs) {
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		Queue<String> queue = new LinkedList<String>(msgs);
		try {
			while (queue.size() > 0) {
				producer.send(new ProducerRecord<String, String>(topic, queue.poll())).get();
				producer.flush();
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}
	
	/**
	 * 同步生产消息
	 * @param msgs
	 */
	public void sender(String msg) {
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		try {
			RecordMetadata record = producer.send(new ProducerRecord<String, String>(topic, msg)).get();
			producer.flush();
			System.out.println(record.offset());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}

	@SuppressWarnings("all")
	public static void main(String[] args) throws Exception {
		String topic = "test";
		Properties props = new Properties() {
			{
				setProperty("bootstrap.servers", "bigdata-cdh01:9092");
				setProperty("zookeeper.connect", "bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181");
				setProperty("group.id", "test");
				setProperty("enable.auto.commit", "false");
				setProperty("auto.commit.interval.ms", "60000");
				setProperty("auto.offset.reset", "earliest");// latest, earliest, none
				setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
				setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
				setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			}
		};
		DWKafkaProducer create = DWKafkaProducer.create(topic, props);
		//create.asycnSender(DataService.list());
		//create.senders(DataService.list());
		List<String> list = DataService.list();
		for (String msg : list) {
			Thread.sleep(500);
			create.sender(msg);
		}
	}

}
