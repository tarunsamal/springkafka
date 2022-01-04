package com.tarun.springkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ManualConsumerService {

    @Autowired
    private Consumer<String,Object> manualConsumer;

    public List<Object> receiveMessages(String topicName, int partition , int offset)
    {
        TopicPartition topicPartition = new TopicPartition(topicName,partition);
        manualConsumer.assign(Arrays.asList(topicPartition)); //subscribe
        manualConsumer.seek(topicPartition,offset);

        ConsumerRecords<String, Object> records = manualConsumer.poll(Duration.ofMillis(1000));
        for(ConsumerRecord<String,Object> record : records)
        {
            log.info("Record {}",record);
        }

        manualConsumer.unsubscribe();//close listener

        return StreamSupport.stream(records.spliterator(),false)
                .map(r->r.value())
                .collect(Collectors.toList());

    }
}
