package com.tarun.springkafka.consumer;

import com.tarun.springkafka.model.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageListener {

    @KafkaListener(topics = "${kafka.first-topic}" , containerFactory = "firstKafkaListenerContainerFactory")
    public void listenFirstTopic1(Object record)
    {
        log.info("Received1 Message from group1 : {}",record);
    }

/*    @KafkaListener(topics = "${kafka.first-topic}" , containerFactory = "firstKafkaListenerContainerFactory")
    public void listenFirstTopic2(Object record)
    {
        log.info("Received2 Message from group1 : {}",record);
    }*/

    @KafkaListener(topics = "${kafka.first-topic}",groupId = "consumerGroup2", containerFactory = "firstKafkaListenerContainerFactory")
    public void listenFirstTopic2(Object record)
    {
        log.info("Received2 Message from group1 : {}",record);
    }

    @KafkaListener(topics = "${kafka.first-topic}",groupId = "consumerGroup3", containerFactory = "firstKafkaListenerContainerFactory")
    public void listenFirstTopicWithDetails(ConsumerRecord<String, MessageEntity> consumerRecord,
                                            @Payload MessageEntity messageEntity,
                                            @Header(KafkaHeaders.GROUP_ID) String groupId,
                                            @Header(KafkaHeaders.OFFSET) int offset,
                                            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition)
    {
        log.info("--------- Received Message-------");
        log.info("Record: {}",consumerRecord);
        log.info("Message: {}",messageEntity);
        log.info("GroupId: {}",groupId);
        log.info("Offset: {}",offset);
        log.info("Partition: {}",partition);
        log.info("<--------- Received Message------->");
    }

    @KafkaListener(topics = "${kafka.first-topic}",
            groupId = "consumerGroup4",
            containerFactory = "firstKafkaListenerContainerFactory",
            topicPartitions = @TopicPartition(topic ="${kafka.first-topic}" ,
                    partitionOffsets = @PartitionOffset(partition = "0",initialOffset = "0"))
    )
    public void listenFirstTopicFromBegin(Object record)
    {
        log.info("Received Message from group4 : {}",record);
    }
}
