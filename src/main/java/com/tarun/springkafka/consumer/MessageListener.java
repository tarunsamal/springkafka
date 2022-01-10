package com.tarun.springkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageListener {

    @KafkaListener(topics = "${kafka.first-topic}" , containerFactory = "firstKafkaListenerContainerFactory")
    public void listenFirstTopic1(Object record)
    {
        log.info("Received1 Message from group1 : {}",record);
    }

    @KafkaListener(topics = "${kafka.first-topic}" , containerFactory = "firstKafkaListenerContainerFactory")
    public void listenFirstTopic2(Object record)
    {
        log.info("Received2 Message from group1 : {}",record);
    }

}
