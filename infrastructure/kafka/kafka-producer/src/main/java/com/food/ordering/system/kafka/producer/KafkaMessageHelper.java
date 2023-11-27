package com.food.ordering.system.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class KafkaMessageHelper {
    public <T> BiConsumer<SendResult<String, T>, Throwable> getKafkaCallback(String topicName, T avroModel, String orderId, String avroModelName) {
        return new BiConsumer<SendResult<String, T>, Throwable>() {
            @Override
            public void accept(SendResult<String, T> result, Throwable throwable) {
                if (throwable != null) {
                    log.error("Error while sending "+ avroModelName + "message {} to topic {}", avroModel.toString(), topicName, throwable);
                }else {
                    RecordMetadata metadata = result.getRecordMetadata();
                    log.info("Received successful response from kafka for order id: {}" +
                                    "Topic: {} Partition: {} Offset: {} Timestamp: {}",
                            orderId,
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset(),
                            metadata.timestamp());
                }
            }
        };
    }
}
