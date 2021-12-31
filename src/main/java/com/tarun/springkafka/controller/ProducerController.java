package com.tarun.springkafka.controller;

import com.tarun.springkafka.model.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("api/producer")
public class ProducerController {

    @Value("${kafka.first-topic}")
    private String FIRST_TOPIC;

    @Autowired
    private KafkaTemplate<String,Object> kafkaProducerTemplate;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage()
    {
        MessageEntity messageEntity = new MessageEntity("default", LocalDateTime.now());
        ListenableFuture<SendResult<String, Object>> send = kafkaProducerTemplate.send(FIRST_TOPIC, messageEntity);

        //Synchronous
        /*try {
            SendResult<String, Object> result = send.get();
            log.info("Message sent success , partition {} offset {}",result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
        } catch (InterruptedException e) {
            log.error("Message Sending Failed",e);
        } catch (ExecutionException e) {
            log.error("Message Sending Failed",e);
        }*/

        //Asynchronous
        send.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Message Sending Failed",ex);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Message sent success , partition {} offset {}",result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });

        return ResponseEntity.ok(messageEntity);
    }
}
