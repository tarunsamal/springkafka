# springkafka
A simple kafka project with spring

Start Zookeeper:
bin/zookeeper-server-start.sh config/zookeeper.properties

copy server.properties to server2.properties with
broker.id=2
listeners=PLAINTEXT://:9093
log.dirs=/tmp/kafka2-logs

Start broker 1 : bin/kafka-server-start.sh config/server.properties
Start broker 2 : bin/kafka-server-start.sh config/server2.properties
