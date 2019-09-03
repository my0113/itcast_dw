package cn.itcast.dw.realtime.utils;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.serializer.StringEncoder;
import kafka.utils.ZkUtils;

import java.util.Properties;

import org.apache.kafka.common.security.JaasUtils;

import cn.itcast.dw.realtime.Configure;

/**
 * Kafka生产者工具类
 */
@SuppressWarnings("all")
public class KafkaAdmin {

	
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
	
}