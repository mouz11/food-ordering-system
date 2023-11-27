package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.producer.KafkaMessageHelper;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.food.ordering.system.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.service.domain.event.OrderCanceledEvent;
import com.food.ordering.system.service.domain.ports.output.message.publisher.payment.OrderCanceledPaymentRequestPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CancelOrderKafkaMessagePublisher implements OrderCanceledPaymentRequestPublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;

    public CancelOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                            OrderServiceConfigData orderServiceConfigData,
                                            KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer, KafkaMessageHelper kafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(OrderCanceledEvent orderCanceledEvent) {
        String orderId = orderCanceledEvent.getOrder().getId().getValue().toString();
        log.info("Received OrderCancelEvent for id: {}", orderId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper
                    .orderCancelledEventToPaymentRequestAvroModel(orderCanceledEvent);

            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel,
                    kafkaMessageHelper.
                            getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(),
                                    paymentRequestAvroModel,
                                    orderId,
                                    "PaymentRequestAvroModel"));
            log.info("PaymentRequestAvroModel sent to Kafka for order id: {}",
                    paymentRequestAvroModel.getOrderId());
        }catch (Exception ex) {
            log.error("Error while sending PaymentRequestAvroModel message" +
                    " to kafka with order id: {}, error: {}", orderId, ex.getMessage());
        }
    }
}
