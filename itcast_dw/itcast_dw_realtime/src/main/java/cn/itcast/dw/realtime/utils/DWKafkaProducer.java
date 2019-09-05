package cn.itcast.dw.realtime.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 造数据程序
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

}
