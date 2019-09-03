package cn.itcast.dw.realtime;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 系统配置，默认加载config.properties全局配置
 * Created by: mengyao
 * 2019年6月23日
 */
public class Configure {
	
    // 使用资源加载器去自动去加载CLASSPATH中的config.properties配置文件
    private static ResourceBundle resourceBundle = 
    		//ResourceBundle.getBundle("canal/canal", new Locale("zh", "CN"));
    		ResourceBundle.getBundle("config", new Locale("zh", "CN"));

    // 使用ResourceBundle.getString方法来读取配置
    public static String canalHost = resourceBundle.getString("canal.host");
    public static String canalPort = resourceBundle.getString("canal.port");
    public static String canalInstance = resourceBundle.getString("canal.instance");
    public static boolean canalWaitPerm = Boolean.parseBoolean(resourceBundle.getString("canal.wait.perm"));
    public static String mysqlDriver = resourceBundle.getString("mysql.driver");
    public static String mysqlUrl = resourceBundle.getString("mysql.url");
    public static String mysqlUsername = resourceBundle.getString("mysql.username");
    public static String mysqlPassword = resourceBundle.getString("mysql.password");
    public static String kafkaBootstrapServers = resourceBundle.getString("kafka.bootstrap.servers");
    public static String kafkaZookeeperConnect = resourceBundle.getString("kafka.zookeeper.connect");
    public static String kafkaOrderTopic = resourceBundle.getString("kafka.order.topic");
    public static String kafkaSerializer = resourceBundle.getString("kafka.serializer");
    public static String kafkaDeSerializer = resourceBundle.getString("kafka.deserializer");
    public static String kafkaOrderRelateTopic = resourceBundle.getString("kafka.order.relate.topic");
    public static String redisHost = resourceBundle.getString("redis.host");
    public static int redisPort = Integer.parseInt(resourceBundle.getString("redis.port"));
    public static String hbaseRootDir = resourceBundle.getString("hbase.rootdir");
    public static String readTopic = resourceBundle.getString("app.read.topic");
    public static String writeTopic = resourceBundle.getString("app.write.topic");

    public static void main(String[] args) {
    	System.out.println(canalWaitPerm);
        System.out.println(canalHost);
        System.out.println(canalPort);
        System.out.println(canalInstance);
        System.out.println(mysqlDriver);
        System.out.println(mysqlUrl);
        System.out.println(mysqlUsername);
        System.out.println(mysqlPassword);
        System.out.println(kafkaBootstrapServers);
        System.out.println(kafkaZookeeperConnect);
        System.out.println(kafkaOrderTopic);
        System.out.println(kafkaOrderRelateTopic);
    }
    
}
