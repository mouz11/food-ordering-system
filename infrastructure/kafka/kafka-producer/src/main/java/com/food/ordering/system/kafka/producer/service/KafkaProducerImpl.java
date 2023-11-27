package com.food.ordering.system.kafka.producer.service;

import com.food.ordering.system.kafka.producer.exception.KafkaProducerException;
import com.google.common.util.concurrent.ListenableFuture;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Component
@Slf4j
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K,V>{
    private final KafkaTemplate<K,V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, K key, V message, BiConsumer<SendResult<K, V>, Throwable> callback) {
        try {
            log.info("Sending message={} to topic={}", message, topicName);
            CompletableFuture<SendResult<K,V>> kafkaResultFuture = kafkaTemplate.send(topicName, key, message);
            kafkaResultFuture.whenComplete(callback);
        }catch (KafkaException ex) {
            log.error("ERROR on kafka Producer with key: {}, message: {} and exception: {}", key, message, ex.getMessage());
            throw new KafkaProducerException("Error on kafka producer with key: " + key + " and message " + message);
        }
    }
    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}
