# 千亿级实时数据仓库

## 1. 集群规划

| 主机名        | IP             | 软件                                    |
| ------------- | -------------- | --------------------------------------- |
| bigdata-cdh01 | 192.168.10.140 | ZooKeeper<br/>Kafka<br/>Druid<br/>Flink |
| bigdata-cdh02 | 192.168.10.150 | ZooKeeper<br/>Kafka<br/>Druid<br/>Flink |
| bigdata-cdh03 | 192.168.10.160 | ZooKeeper<br/>Kafka<br/>Druid<br/>Flink |



## 2. 软件安装

所有的软件安装在/export/servers/目录下，原始的gzip包在/export/softwares/目录下。

### 2.1 配置用户环境变量

```shell
## 编辑用户目录下的.bash_profile
vim /root/.bash_profile
## 新增
PATH=$PATH:$HOME/bin
HADOOP_HOME=/export/servers/hadoop
HBASE_HOME=/export/servers/hbase
HIVE_HOME=/export/servers/hive
ZOOKEEPER_HOME=/export/servers/zookeeper
FLUME_HOME=/export/servers/flume
SQOOP_HOME=/export/servers/sqoop
KAFKA_HOME=/export/servers/kafka
DRUID_HOME=/export/servers/druid
FLINK_HOME=/export/servers/flink
PATH=.$FLINK_HOME/bin:$DRUID_HOME/bin:$KAFKA_HOME/bin:$SQOOP_HOME/bin:$FLUME_HOME/bin:$ZOOKEEPER_HOME/bin:$HIVE_HOME/bin:$HBASE_HOME/bin:$HADOOP_HOME/bin:$PATH
export PATH
## 保存退出
shift+ZZ
```

### 2.2 安装MySQL-5.6

```shell
## 下载MySQL5.6的yum源
wget http://repo.mysql.com/mysql-community-release-el6-5.noarch.rpm

## 安装MysQL
rpm -ivh mysql-community-release-el6-5.noarch.rpm
yum install mysql-community-server

## 启动MySQL服务
service mysqld start
/usr/bin/mysqladmin -u root password '123456'
/usr/bin/mysqladmin -u root -h bigdata-cdh01 password '123456'
chkconfig mysqld on

## 设置MySQL允许root用户从任意地址登录
mysql -uroot -p123456
mysql>grant all privileges on *.* to 'root'@'%' identified by '123456' with grant option;
mysql>flush privileges;
```

### 2.3 安装Redis-4.0.6

```shell
cd /export/softwares/
	
## 下载redis
wget http://download.redis.io/releases/redis-4.0.6.tar.gz
tar -zxf redis-4.0.6.tar.gz -C /export/servers/

cd /export/servers
## 创建redis软连接
ln -s redis-4.0.6 redis

cd redis
## 安装cmake
yum install gcc
make MALLOC=libc
## 编译redis
cd src && make install
## 配置redis
vim /redis.conf
	daemonize yes
	bind 192.168.10.140
shift+ZZ

mkdir /etc/redis
## 创建位于/etc/redis/自启动的配置文件
ln -s /export/servers/redis-4.0.6/redis.conf /etc/redis/6379.conf
cp utils/redis_init_script /etc/init.d/redisd
vim /etc/init.d/redisd（在#!/bin/sh下一行新增如下）
# chkconfig:   2345 90 10
# description:  Redis is a persistent key-value database
shift+ZZ

cd /etc/init.d
## 设置redis开机启动
chkconfig redisd on
service redisd start
```

### 2.4 安装kafka-0.11.0.2

#### 2.4.1 安装kafka

```shell
cd /export/softwares/
	wget http://archive.apache.org/dist/kafka/0.11.0.2/kafka_2.11-0.11.0.2.tgz
	tar -zxf kafka_2.11-0.11.0.2.tgz -C /export/servers/
cd /export/servers
	ln -s kafka_2.11-0.11.0.2 kafka
cd kafka
	mkdir -p ./data/logs
	vim config/server.properties
		broker.id=1
		listeners=PLAINTEXT://bigdata-cdh01:9092
		log.dirs=/export/servers/kafka/data/logs
		zookeeper.connect=bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181
		delete.topic.enable=true
	shift+ZZ
scp -r kafka_2.11-0.11.0.2 root@bigdata-cdh02:/export/servers/
	vim /export/servers/kafka/config/server.properties
		broker.id=2
		listeners=PLAINTEXT://bigdata-cdh02:9092
		log.dirs=/export/servers/kafka/data/logs
		zookeeper.connect=bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181
		delete.topic.enable=true
	shift+ZZ
scp -r kafka_2.11-0.11.0.2 root@bigdata-cdh03:/export/servers/
	vim /export/servers/kafka/config/server.properties
		broker.id=3
		listeners=PLAINTEXT://bigdata-cdh03:9092
		log.dirs=/export/servers/kafka/data/logs
		zookeeper.connect=bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181
		delete.topic.enable=true
	shift+ZZ
```

#### 2.4.2 测试kafka

```shell
## 启动：
kafka-server-start.sh -daemon $KAFKA_HOME/config/server.properties
## 创建test主题：
kafka-topics.sh --create --zookeeper bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181 --replication-factor 3 --partitions 1 --topic test
## 查看主题列表：
kafka-topics.sh --list --zookeeper bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181
## 查看test主题状态
kafka-topics.sh --describe --zookeeper bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181 --topic test
## 查看所有主题状态：
kafka-topics.sh --describe --zookeeper bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181
## 启动生产者生产数据：
kafka-console-producer.sh --broker-list bigdata-cdh01:9092 --topic test
## 启动消费者消费数据：
kafka-console-consumer.sh --zookeeper bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181 --from-beginning --topic test
## 删除主题（delete.topic.enable=true）：
kafka-topics.sh --delete --zookeeper bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181 --topic test
## 修改kafka关闭脚本（否则提示No kafka server to stop）：
注释：#PIDS=$(ps ax | grep -i 'kafka\.Kafka' | grep java | grep -v grep | awk '{print $1}')
新增：PIDS=$(jps -lm | grep -i 'kafka.Kafka' | awk '{print $1}')
kafka-server-stop.sh
```

#### 2.4.3 安装kafka的WebUI插件

```shell
## 扩展kafka offset监控插件（bigdata-cdh01）
wget -o /export/servers/kafka/libs/KafkaOffsetMonitor-assembly-0.2.1.jar https://github.com/quantifind/KafkaOffsetMonitor/releases/download/v0.2.1/KafkaOffsetMonitor-assembly-0.2.1.jar
cd /export/servers/kafka/
vim kafka-monitor.sh
	#!/bin/bash
	java -cp KafkaOffsetMonitor-assembly-0.2.1.jar com.quantifind.kafka.offsetapp.OffsetGetterWeb --zk bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181/kafka --port 9099 --refresh 10.seconds --retain 2.days
	shift+ZZ
	chmod 755 kafka-monitor.sh
	nohup kafka-monitor.sh &
	http://bigdata-cdh01:9099/
```

### 2.5 安装imply-3.0.4

#### 2.5.1 创建MySQL的druid和pivot数据库

```mysql
-- 先初始化MySQL，版本必须使用5.5及以上版本（Druid和Pivot使用utf8mb4字符集）。
CREATE DATABASE `druid` DEFAULT CHARACTER SET utf8mb4;
CREATE DATABASE `pivot` DEFAULT CHARACTER SET utf8mb4;
```

#### 2.5.2 安装druid-0.15.0-incubating

```shell
## 解压druid
tar -zxvf /export/softwares/imply-3.0.4.tar.gz -C /export/servers/

## 进入druid安装目录
cd /export/servers/imply-3.0.4/（在bigdata-cdh01上配置，然后分发到bigdata-cdh02和bigdata-cdh03）

## 1、配置druid的公共配置文件
vim conf/druid/_common/common.runtime.properties
# Extensions
druid.extensions.directory=dist/druid/extensions
druid.extensions.hadoopDependenciesDir=dist/druid/hadoop-dependencies
druid.extensions.loadList=["druid-kafka-indexing-service","mysql-metadata-storage"]
# Logging Log all runtime properties on startup. Disable to avoid logging properties on startup:
druid.startup.logging.logProperties=true
# Zookeeper
druid.zk.service.host=bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181
druid.zk.paths.base=/druid
# Metadata storage For MySQL:
druid.metadata.storage.type=mysql
druid.metadata.storage.connector.connectURI=jdbc:mysql://bigdata-cdh01:3306/druid
druid.metadata.storage.connector.user=root
druid.metadata.storage.connector.password=123456
# Deep storage For local disk (only viable in a cluster if this is a network mount):
druid.storage.type=local
druid.storage.storageDirectory=var/druid/segments
# For HDFS:
#druid.storage.type=hdfs
#druid.storage.storageDirectory=/druid/segments
# Indexing service logs For local disk (only viable in a cluster if this is a network mount):
druid.indexer.logs.type=file
druid.indexer.logs.directory=var/druid/indexing-logs
# For HDFS:
#druid.indexer.logs.type=hdfs
#druid.indexer.logs.directory=/druid/indexing-logs
# Service discovery
druid.selectors.indexing.serviceName=druid/overlord
druid.selectors.coordinator.serviceName=druid/coordinator
# Monitoring
druid.monitoring.monitors=["org.apache.druid.java.util.metrics.JvmMonitor"]
druid.emitter=logging
druid.emitter.logging.logLevel=debug

## 2、配置druid的overlord的jvm.config
vim  conf/druid/overlord/jvm.config
-server
-Xms3g
-Xmx3g
-XX:+ExitOnOutOfMemoryError
-Duser.timezone=UTC+8
-Dfile.encoding=UTF-8
-Djava.io.tmpdir=var/tmp
-Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager

## 3、配置druid的overlord的runtime.properties
vim conf/druid/overlord/runtime.properties
druid.service=druid/overlord
druid.port=8090
druid.indexer.queue.startDelay=PT30S
druid.indexer.runner.type=remote
druid.indexer.storage.type=metadata

## 4、配置druid的coordinator的jvm.config
vim conf/druid/coordinator/jvm.config
-server
-Xms3g
-Xmx3g
-XX:+ExitOnOutOfMemoryError
-Duser.timezone=UTC+8
-Dfile.encoding=UTF-8
-Djava.io.tmpdir=var/tmp
-Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager
-Dderby.stream.error.file=var/druid/derby.log

## 5、配置druid的coordinator的runtime.properties
vim conf/druid/coordinator/runtime.properties
druid.service=druid/coordinator
druid.port=8081
druid.coordinator.startDelay=PT30S
druid.coordinator.period=PT30S

## 6、配置druid的middleManager的jvm.config
vim conf/druid/middleManager/jvm.config
-server
-Xms128m
-Xmx128m
-XX:+ExitOnOutOfMemoryError
-Duser.timezone=UTC+8
-Dfile.encoding=UTF-8
-Dhadoop.hadoop.tmp.dir=var/hadoop-tmp
-Djava.io.tmpdir=var/tmp
-Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager

## 7、配置druid的middlemanager的runtime.properties
vim conf/druid/middleManager/runtime.properties
druid.service=druid/middlemanager
druid.port=8091
# Number of tasks per middleManager
druid.worker.capacity=3
# Task launch parameters
druid.indexer.runner.javaOpts=-server -Xmx2g -Duser.timezone=UTC+8 -Dfile.encoding=UTF-8 -XX:+ExitOnOutOfMemoryError -Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager
druid.indexer.task.baseTaskDir=var/druid/task
druid.indexer.task.restoreTasksOnRestart=true
# HTTP server threads
druid.server.http.numThreads=10
# Processing threads and buffers
druid.processing.buffer.sizeBytes=100000000
druid.processing.numMergeBuffers=2
druid.processing.numThreads=2
druid.processing.tmpDir=var/druid/processing
# Hadoop indexing
druid.indexer.task.hadoopWorkingPath=var/druid/hadoop-tmp
druid.indexer.task.defaultHadoopCoordinates=["org.apache.hadoop:hadoop-client:2.6.0"]

## 8、配置druid的broker的jvm.config
vim conf/druid/broker/jvm.config
-server
-Xms2g
-Xmx2g
-XX:MaxDirectMemorySize=2g
-XX:+ExitOnOutOfMemoryError
-Duser.timezone=UTC+8
-Dfile.encoding=UTF-8
-Djava.io.tmpdir=var/tmp
-Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager

## 9、配置druid的broker的runtime.properties
vim conf/druid/broker/runtime.properties
druid.service=druid/broker
druid.port=8082
# HTTP server settings
druid.server.http.numThreads=10
# HTTP client settings
druid.broker.http.numConnections=8
druid.broker.http.maxQueuedBytes=50000000
# Processing threads and buffers
druid.processing.buffer.sizeBytes=134217728
druid.processing.numMergeBuffers=2
druid.processing.numThreads=1
druid.processing.tmpDir=var/druid/processing
# Query cache disabled -- push down caching and merging instead
druid.broker.cache.useCache=false
druid.broker.cache.populateCache=false
# SQL
druid.sql.enable=true

## 10、配置druid的router的jvm.config
vim conf/druid/router/jvm.config
-server
-Xms512m
-Xmx512m
-XX:+UseG1GC
-XX:MaxDirectMemorySize=512m
-XX:+ExitOnOutOfMemoryError
-Duser.timezone=UTC+8
-Dfile.encoding=UTF-8
-Djava.io.tmpdir=var/tmp
-Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager

## 11、配置druid的router的runtime.properties
vim conf/druid/router/runtime.properties
druid.service=druid/router
druid.port=8888
druid.processing.numThreads=1
druid.processing.buffer.sizeBytes=1000000
druid.router.defaultBrokerServiceName=druid/broker
druid.router.coordinatorServiceName=druid/coordinator
druid.router.http.numConnections=50
druid.router.http.readTimeout=PT5M
druid.router.http.numMaxThreads=50
druid.server.http.numThreads=50
druid.router.managementProxy.enabled=true

## 12、配置druid的pivot的config.yaml
vim conf/pivot/config.yaml
port: 9095
varDir: var/pivot
servingMode: clustered
initialSettings:
connections:
- name: druid
type: druid
title: My Druid
host: localhost:8888
#
# Pivot must have a state store in order to function
# The state (data cubes, dashboards, etc) can be stored in two ways.
# Choose just one option and comment out the other.
#
#  1) Stored in a sqlite file, editable at runtime with Settings View. Not suitable for running in a cluster.
#  2) Stored in a database, editable at runtime with Settings View. Works well with a cluster of Imply servers.
#

#
# 1) File-backed (sqlite) state (not suitable for running in a cluster)
#

#stateStore:
#  type: sqlite
#  connection: var/pivot/pivot-settings.sqlite
#
# 2) Database-backed state 'mysql' (MySQL) or 'pg' (Postgres)
#
stateStore:
type: mysql
location: mysql
connection: 'mysql://root:123456@bigdata-cdh01:3306/pivot'

## 13、分发bigdata-cdh01上配置好的druid到bigdata-cdh02和bigdata-cdh03节点中
分发到bigdata-cdh02和bigdata-cdh03上
scp -r /export/servers/imply-3.0.4 root@bigdata-cdh02:/export/servers/
scp -r /export/servers/imply-3.0.4 root@bigdata-cdh02:/export/servers/
```

#### 2.5.3 启动druid集群

```shell
## 14、启动druid集群
bigdata-cdh01（使用外部zk而不使用imply自带zk启动overlord和coordinator）:
/export/servers/imply-3.0.4/bin/supervise -c /export/servers/imply-3.0.4/conf/supervise/master-no-zk.conf --daemonize
bigdata-cdh02（启动historical和middlemanager）:
/export/servers/imply-3.0.4/bin/supervise -c /export/servers/imply-3.0.4/conf/supervise/data.conf --daemonize
bigdata-cdh03（启动broker和router）:
/export/servers/imply-3.0.4/bin/supervise -c /export/servers/imply-3.0.4/conf/supervise/query.conf --daemonize

## 15、验证druid集群服务
http://bigdata-cdh01:8081/index.html		（coordinator、overlord）
http://bigdata-cdh01:8090/console.html		（middleManager、historical）
http://bigdata-cdh03:8888					（sql）

## 16、关闭druid集群
for i in `seq 3 -1 1`; do echo bigdata-cdh0$i; ssh bigdata-cdh0$i "source /etc/profile; /export/servers/imply-3.0.4/bin/servise --down"; done

```



### 2.6 安装canal-1.0.19

#### 2.6.1 创建MySQL的canal用户

注意：先在mysql数据库中创建canal用户，确保canal用户存在。

```sql
-- 创建canal用户，密码也是canal
create user 'canal' identified by 'canal';
-- 允许canal用户从任意地址访问任何数据库及其表
grant all privileges on *.* to 'canal'@'%';
-- 刷新权限使修改立即生效
flush privileges;
```

#### 2.6.2 开启mysql的binlog

```properties
## 编辑mysql的配置文件，打开binlog，使用row模式
# For advice on how to change settings please see
# http://dev.mysql.com/doc/refman/5.6/en/server-configuration-defaults.html

[mysqld]
#
# Remove leading # and set to the amount of RAM for the most important data
# cache in MySQL. Start at 70% of total RAM for dedicated server, else 10%.
# innodb_buffer_pool_size = 128M
#
# Remove leading # to turn on a very important data integrity option: logging
# changes to the binary log between backups.
# log_bin
#
# Remove leading # to set options mainly useful for reporting servers.
# The server defaults are faster for transactions and fast SELECTs.
# Adjust sizes as needed, experiment to find the optimal values.
# join_buffer_size = 128M
# sort_buffer_size = 2M
# read_rnd_buffer_size = 2M
datadir=/var/lib/mysql
socket=/var/lib/mysql/mysql.sock
log-bin=/var/lib/mysql/mysql-bin  	#设置mysql的binlog的位置
binlog-format=ROW					#使用row模式
server-id=1							#canal要求指定不重复的serverid

# Disabling symbolic-links is recommended to prevent assorted security risks
symbolic-links=0

# Recommended in standard MySQL setup
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES

[mysqld_safe]
log-error=/var/log/mysqld.log
pid-file=/var/run/mysqld/mysqld.pid
```

#### 2.6.3 安装canal

```shell
## 解压canal安装包到/export/servers目录下
tar -zxvf /export/softwares/canal.deployer-1.0.19.tar.gz -C /export/servers

cd /export/servers/canal

## 修改canal配置文件（监控所有数据库的所有表）
vim /export/servers/canal/conf/example/instance.properties
## mysql serverId
canal.instance.mysql.slaveId = 1234
# position info
canal.instance.master.address = 127.0.0.1:3306
canal.instance.master.journal.name =
canal.instance.master.position =
canal.instance.master.timestamp =
#canal.instance.standby.address =
#canal.instance.standby.journal.name =
#canal.instance.standby.position =
#canal.instance.standby.timestamp =
# username/password
#修改这两项
canal.instance.dbUsername = canal
canal.instance.dbPassword = canal
canal.instance.defaultDatabaseName =
canal.instance.connectionCharset = UTF-8
# table regex
canal.instance.filter.regex = .*\\..*
# table black regex
canal.instance.filter.black.regex =
```

#### 2.6.4 启动canal

```shell
## 启动canal
/export/servers/canal/bin/startup.sh

## 查看canal进程
[root@bigdata-cdh01 ~]# jps
3121 CanalLauncher
3142 Jps
```

## 3. 数仓开发

### 3.1 数据源

位于MySQL中的数据库表，包括

​		**订单表wst_orders**、

​		**订单商品表wst_order_goods**

​		**行为数据表wst_logs**（该表在MySQL中仅存储3个月的行为数据）。

数据采集方式：

| canal-1.0.19                                                 | flume-1.6.0                   |
| ------------------------------------------------------------ | ----------------------------- |
| wst_orders表数据迁移到kafka中<br/>wst_order_goods表数据迁移到redis中 | wst_orders表数据迁移到kafka中 |



### 3.2 启动集群服务

#### 3.2.1 启动MySQL服务（在bigdata-cdh01节点上）

```shell
/sbin/service mysqld start
```

#### 3.2.2 启动zookeeper集群

```shell
for i in `seq 1 3`; do echo bigdata-cdh0$i; ssh bigdata-cdh0$i "source /etc/profile; /export/servers/zookeeper/bin/zkServer.sh start"; done
```

#### 3.2.3 启动kafka集群

```shell
for i in `seq 1 3`; do echo bigdata-cdh0$i; ssh bigdata-cdh0$i "source /etc/profile; /export/servers/kafka/bin/kafka-server-start.sh -daemon /export/servers/kafka/config/server.properties"; done
```

#### 3.2.4 启动druid集群

​		**bigdata-cdh01（使用外部zk而不使用imply自带zk启动overlord和coordinator）:**

```shell
/export/servers/imply-3.0.4/bin/supervise -c /export/servers/imply-3.0.4/conf/supervise/master-no-zk.conf --daemonize

```

​		**bigdata-cdh02（启动historical和middlemanager）:**

```shell
/export/servers/imply-3.0.4/bin/supervise -c /export/servers/imply-3.0.4/conf/supervise/data.conf --daemonize

```

​		**bigdata-cdh03（启动broker和router）:**

```shell
/export/servers/imply-3.0.4/bin/supervise -c /export/servers/imply-3.0.4/conf/supervise/query.conf --daemonize

```

#### 3.2.5 启动hadoop集群

​		**在bigdata-cdh01节点上：**

```shell
/export/servers/hadoop/sbin/start-dfs.sh

```

​		**在bigdata-cdh02节点上：**

```shell
/export/servers/hadoop/sbin/start-yarn.sh

```

#### 3.2.6 启动hbase集群（在bigdata-cdh01节点上）

```shell
/export/servers/hbase/bin/start-hbase.sh

```

#### 3.2.7 启动Redis服务（在bigdata-cdh01节点上）

```shell
/sbin/service redisd start

```

#### 3.2.8 启动Canal服务（在bigdata-cdh01节点上）

```shell
/export/servers/canal/bin/startup.sh

```

#### 3.2.9 验证所有节点运行的进程

```shell
for i in `seq 1 3`; do echo bigdata-cdh0$i; ssh bigdata-cdh0$i `which jps`; done

```

![](C:\Users\78708\OneDrive\文档\应用系统\千亿级实时数据仓库\imgs\运行进程检查.png)



### 3.3 初始化程序

**初始化kafka**

canal采集数据写入到kafka的主题

```shell
kafka-topics.sh --create --zookeeper bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181 --replication-factor 3 --partitions 1 --topic order

```

flink计算完成后输出到kafka的主题（druid-kafka-index的数据）

```shell
kafka-topics.sh --create --zookeeper bigdata-cdh01:2181,bigdata-cdh02:2181,bigdata-cdh03:2181 --replication-factor 3 --partitions 1 --topic dws_od1

```

**hbase订单明细表**

```shell
create 'dwd_order_detail','detail'

```

**Redis**

```shell
## 
redis-cli -h 192.168.10.140
set  quota_visitor  "{\"pv\":16,\"uv\":6,\"ip\":9}"
set quota_convert "{\"bn\":3782329,\"cn\":698341,\"on\":219935,\"pn\":105071}"

```

**程序运行顺序**

```txt
## 1、运行CanalClient采集端
	itcast_dw_transfer工程的org.itcast.dw.transfer.canal.CanalClient
## 2、运行Flink应用
	itcast_dw_realtime工程cn.itcast.dw.realtime.apps.App
## 3、运行模拟数据程序
	itcast_dw_realtime工程cn.itcast.dw.realtime.generater.DataGeneratedApp
## 4、启动实时数仓可视化WebUI
	4.1、配置IDEA的maven-jetty插件
		首先在itcast_dw_web工程中，选中File->Settings
		然后配置IDEA的Run参数->Edit Configuration->点击+号按钮->新增Maven的Jetty插件->配置参数
			1、command line = jetty:run
			2、配置项目绝对路径
	4.2、打开http://localhost:8080/itcast_edw_web

```

​	



​	

​	