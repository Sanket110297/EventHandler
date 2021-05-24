package com.eh.producer;

import com.eh.domain.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class EventsProducer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    ObjectMapper mapper;

    public void sendEvent(Event event) throws JsonProcessingException {
        Integer key = event.getEventId();
        String value = mapper.writeValueAsString(event.getBook());
        /**
         * ListenableFuture is reference for something that is going to happen in future
         */
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.
                sendDefault(key, value);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override public void onFailure(Throwable throwable) {
                handleFailure(key, value, throwable);
            }

            @Override public void onSuccess(SendResult<Integer, String> integerStringSendResult) {
                handleSuccess(key, value, integerStringSendResult);
            }
        });
    }

    private void handleFailure(Integer key, String value, Throwable throwable) {
        log.error("Error sending message and the exception is {}", throwable.getMessage());
        try {
            throw throwable;
        } catch (Throwable e) {
            log.error("Error in OnFailure: {}", e.getMessage());
        }
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Successfuly sent message for the key : {} and the value : {} , partition is {}"
                ,key, value ,result.getRecordMetadata().partition());
    }

}
