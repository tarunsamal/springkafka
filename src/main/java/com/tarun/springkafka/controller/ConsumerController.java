package com.tarun.springkafka.controller;

import com.tarun.springkafka.consumer.ManualConsumerService;
import com.tarun.springkafka.model.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("api/consumer")
public class ConsumerController {

    @Value("${kafka.first-topic}")
    private String FIRST_TOPIC;

    @Autowired
    private ManualConsumerService manualConsumerService;

    @GetMapping("/manual")
    public ResponseEntity<?> getMessagesManually(
            @RequestParam(value = "partition" , required = false , defaultValue = "0") int partition,
            @RequestParam(value = "offset" , required = false , defaultValue = "0") int offset) {
        return ResponseEntity.ok(manualConsumerService.receiveMessages(FIRST_TOPIC,partition,offset));
    }
}
