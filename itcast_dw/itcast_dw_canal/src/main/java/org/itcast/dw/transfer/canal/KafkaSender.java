package org.itcast.dw.transfer.canal;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.serializer.StringEncoder;
import kafka.utils.ZkUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.security.JaasUtils;

/**
 * Kafka生产者工具类
 */
public class KafkaSender {

	
	@SuppressWarnings("serial")
    private static Properties props = new Properties() {{
    	put("auto.create.topics.enable", "true");
    	put("bootstrap.servers" , Configure.kafkaBootstrapServers);
    	put("zookeeper.connect" , Configure.kafkaZookeeperConnect);
    	put("key.serializer", Configure.kafkaSerializer);
    	put("key.deserializer", Configure.kafkaDeSerializer);
    	put("value.serializer", Configure.kafkaSerializer);
    	put("serializer.class" , StringEncoder.class.getName());
    }};

    /**
     * 发送消息到Kafka指定topic
     * @param topic topic名字
     * @param key 键值
     * @param data 数据
     */
	public static void sendMessage(String topic, String key, String data) {
		KafkaProducer<String, String> producer = createProducer();
		producer.send(new ProducerRecord<String, String>(topic, key, data));
		producer.close();
	}

    /**
     * 创建kafka生产者实例
     * @return
     */
    private static KafkaProducer<String , String> createProducer(){
        return new KafkaProducer<String, String>(props);
    }
    
    /**
     * 创建kafka主题，分区为1，副本为1
     * @param zkHosts
     * @param topic
     */
    public static void createTopic(String zkHosts, String topic) {
		ZkUtils zkUtils = ZkUtils.apply(zkHosts, 30000, 30000, JaasUtils.isZkSecurityEnabled());
		AdminUtils.createTopic(zkUtils, topic, 1, 1, new Properties(), new RackAwareMode.Enforced$());
		zkUtils.close();
	}
    
    /**
     * 创建kafka主题
     * @param zkHosts
     * @param topic
     * @param partition
     * @param replication
     */
	public static void createTopic(String zkHosts, String topic, int partition, int replication) {
		ZkUtils zkUtils = ZkUtils.apply(zkHosts, 30000, 30000, JaasUtils.isZkSecurityEnabled());
		AdminUtils.createTopic(zkUtils, topic, partition, replication, new Properties(), new RackAwareMode.Enforced$());
		zkUtils.close();
	}

	/**
	 * 删除kafka主题
	 * @param zkHosts
	 * @param topic
	 */
	public static void deleteTopic(String zkHosts, String topic) {
		ZkUtils zkUtils = ZkUtils.apply(zkHosts, 30000, 30000, JaasUtils.isZkSecurityEnabled());
		AdminUtils.deleteTopic(zkUtils, topic);
		zkUtils.close();
	}

	/**
	 * 如果kafka主题不存在则创建
	 * @param zkHosts
	 * @param topic
	 */
	public static void topicIfNoExistCreate(String zkHosts, String topic) {
		ZkUtils zkUtils = ZkUtils.apply(zkHosts, 30000, 30000, JaasUtils.isZkSecurityEnabled());
		if(!AdminUtils.topicExists(zkUtils, topic)) {
			createTopic(zkHosts, topic);
		}
	}
	
	/**
	 * 异步生产消息
	 * @param msgs
	 */
	public void asycnSenders(String topic, Collection<String> msgs) {
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
	public void senders(String topic, Collection<String> msgs) {
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
	public void sender(String topic, String msg) {
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