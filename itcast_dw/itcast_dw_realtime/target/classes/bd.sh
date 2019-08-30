#!/bin/sh

cmd=$1

if [[ $cmd = "start" ]]; then
        echo "==== start zookeeper ===="
        for i in `seq 1 3`; do echo bigdata-cdh0$i; ssh bigdata-cdh0$i "source /etc/profile; /export/servers/zookeeper/bin/zkServer.sh start"; done
        echo "==== start kafka     ===="
        for i in `seq 1 3`; do echo bigdata-cdh0$i; ssh bigdata-cdh0$i "source /etc/profile; /export/servers/kafka/bin/kafka-server-start.sh -daemon /export/servers/kafka/config/server.properties"; done
        echo "==== start hadoop    ===="
        for i in `seq 1 2`;
                do echo bigdata-cdh0$i;
                if [[ $i = 1 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/hadoop/sbin/start-dfs.sh";
                elif [[ $i = 2 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/hadoop/sbin/start-yarn.sh";
                fi
        done
        echo "==== start hbase     ===="
        for i in `seq 1 3`;
                do echo bigdata-cdh0$i;
                if [[ $i = 1 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/hbase/bin/start-hbase.sh";
                fi
        done
        echo "==== start hive      ===="
        for i in `seq 1 2`;
                do echo bigdata-cdh0$i;
                if [[ $i = 1 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/hive/bin/hive --service metastore >/dev/null 2>&1 &";
                elif [[ $i = 2 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/hive/bin/hive --service hiveserver2 >/dev/null 2>&1 &";
                fi
        done
        echo "==== start druid     ===="
        for i in `seq 3 -1 1`;
                do echo bigdata-cdh0$i;
                if [[ $i = 3 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/druid/bin/broker.sh start";
                elif [[ $i = 2 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/druid/bin/historical.sh start; /export/servers/druid/middleManager.sh start";
                elif [[ $i = 1 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/druid/bin/coordinator.sh start; /export/servers/druid/overlord.sh start";
                fi
        done
elif [[ $cmd = "stop" ]]; then
        echo "==== stop druid     ===="
        for i in `seq 1 3`;
                do echo bigdata-cdh0$i;
                if [[ $i = 1 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/druid/bin/overlord.sh stop; /export/servers/druid/coordinator.sh stop";
                elif [[ $i = 2 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/druid/bin/historical.sh stop; /export/servers/druid/middleManager.sh stop";
                elif [[ $i = 1 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/druid/bin/broker.sh stop";
                fi
        done
        echo "==== stop hive      ===="
        for i in `seq 2 -1 1`;
                do echo bigdata-cdh0$i;
                if [[ $i = 2 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; ps -ef | grep RunJar | grep -v grep | awk  '{print $2}' | xargs  kill -9";
                elif [[ $i = 1 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; ps -ef | grep RunJar | grep -v grep | awk  '{print $2}' | xargs  kill -9";
                fi
        done
        echo "==== stop hbase     ===="
        for i in `seq 1 3`;
                do echo bigdata-cdh0$i;
                if [[ $i = 1 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/hbase/bin/stop-hbase.sh";
                elif [[ $i = 2 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/hbase/bin/hbase-daemon.sh stop hmaster";
                fi
        done
        echo "==== stop hadoop    ===="
        for i in `seq 1 2`;
                do echo bigdata-cdh0$i;
                if [[ $i = 1 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/hadoop/sbin/stop-dfs.sh";
                elif [[ $i = 2 ]]; then
                        ssh bigdata-cdh0$i "source /etc/profile; /export/servers/hadoop/sbin/stop-yarn.sh";
                fi
        done
        echo "==== stop  kafka     ===="
        for i in `seq 1 3`; do echo bigdata-cdh0$i; ssh bigdata-cdh0$i "source /etc/profile; /export/servers/kafka/bin/kafka-server-stop.sh"; done
        echo "==== stop  zookeeper ===="
        for i in `seq 1 3`; do echo bigdata-cdh0$i; ssh bigdata-cdh0$i "source /etc/profile; /export/servers/zookeeper/bin/zkServer.sh stop"; done
else
        echo -e "\nUsage : start or stop\n"
fi

for i in `seq 1 3`; do echo bigdata-cdh0$i "节点当前正在运行的守护进程"; ssh bigdata-cdh0$i `which jps`; done

