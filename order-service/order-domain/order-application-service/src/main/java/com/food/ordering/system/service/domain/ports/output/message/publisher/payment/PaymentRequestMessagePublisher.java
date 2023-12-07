package com.food.ordering.system.service.domain.ports.output.message.publisher.payment;

import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {
    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage,
                 BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
