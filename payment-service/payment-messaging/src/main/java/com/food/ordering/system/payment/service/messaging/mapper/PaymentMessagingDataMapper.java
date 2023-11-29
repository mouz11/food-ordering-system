package com.food.ordering.system.payment.service.messaging.mapper;

import com.food.ordering.system.domain.valueObject.PaymentOrderStatus;
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel;
import com.food.ordering.system.kafka.order.avro.model.PaymentStatus;
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.food.ordering.system.payment.service.domain.event.PaymentCanceledEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMessagingDataMapper {
    public PaymentResponseAvroModel paymentCompletedEventToPaymentResponseAvroModel(PaymentCompletedEvent paymentCompletedEvent) {

        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPaymentId(paymentCompletedEvent.getPayment().getId().getValue().toString())
                .setCustomerId(paymentCompletedEvent.getPayment().getCustomerId().getValue().toString())
                .setOrderId(paymentCompletedEvent.getPayment().getOrderId().getValue().toString())
                .setPrice(paymentCompletedEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentCompletedEvent.getPayment().getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCompletedEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCompletedEvent.getFailureMessages())
                .build();
    }
    public PaymentResponseAvroModel paymentCanceledEventToPaymentResponseAvroModel(PaymentCanceledEvent paymentCanceledEvent) {

        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPaymentId(paymentCanceledEvent.getPayment().getId().getValue().toString())
                .setCustomerId(paymentCanceledEvent.getPayment().getCustomerId().getValue().toString())
                .setOrderId(paymentCanceledEvent.getPayment().getId().getValue().toString())
                .setPrice(paymentCanceledEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentCanceledEvent.getPayment().getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCanceledEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCanceledEvent.getFailureMessages())
                .build();
    }
    public PaymentResponseAvroModel paymentFailedEventToPaymentResponseAvroModel(PaymentFailedEvent paymentFailedEvent) {

        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPaymentId(paymentFailedEvent.getPayment().getId().getValue().toString())
                .setCustomerId(paymentFailedEvent.getPayment().getCustomerId().getValue().toString())
                .setOrderId(paymentFailedEvent.getPayment().getId().getValue().toString())
                .setPrice(paymentFailedEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentFailedEvent.getPayment().getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentFailedEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentFailedEvent.getFailureMessages())
                .build();
    }
    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .id(paymentRequestAvroModel.getId())
                .sagaId(paymentRequestAvroModel.getSagaId())
                .customerId(paymentRequestAvroModel.getCustomerId())
                .orderId(paymentRequestAvroModel.getOrderId())
                .price(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.valueOf(paymentRequestAvroModel.getPaymentOrderStatus().name()))
                .build();
    }
}
