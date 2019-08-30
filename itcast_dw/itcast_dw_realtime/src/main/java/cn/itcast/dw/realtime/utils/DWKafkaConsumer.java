package cn.itcast.dw.realtime.utils;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * 
 * @author mengyao
 *
 */
public class DWKafkaConsumer {

	private String topic;
	private Properties props;

	private DWKafkaConsumer(String topic, Properties props) {
		this.topic = topic;
		this.props = props;
	}

	/**
	 * 创建消费者实例
	 * @param topic
	 * @param props
	 * @return
	 */
	public static DWKafkaConsumer create(String topic, Properties props) {
		return new DWKafkaConsumer(topic, props);
	}

	/**
	 * 
	 */
	public void print() {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		try {
			consumer.subscribe(Arrays.asList(topic));
			ConsumerRecords<String, String> records = consumer.poll(1000);
			records.forEach(record -> {
				System.out.println(record.offset()+": "+record.key()+"="+record.value());
			});
			consumer.commitSync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			consumer.close();
		}
	}

	@SuppressWarnings("all")
	public static void main(String[] args) {
		String topic = "test";
		Properties props = new Properties() {
			{
				setProperty("bootstrap.servers", "bigdata-cdh01:9092");
				setProperty("zookeeper.connect", "bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181");
				setProperty("group.id", "test1");
				setProperty("enable.auto.commit", "false");
				setProperty("auto.commit.interval.ms", "60000");
				setProperty("auto.offset.reset", "earliest");// latest, earliest, none
				setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
				setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
				setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			}
		};
		DWKafkaConsumer create = DWKafkaConsumer.create(topic, props);
		create.print();
	}

}
