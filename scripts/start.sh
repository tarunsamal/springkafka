#Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

#Start First Broker
bin/kafka-server-start.sh config/server.properties

#Start Second Broker
bin/kafka-server-start.sh config/server2.properties

#Check ConsumerGroup Status
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group consumerGroup1