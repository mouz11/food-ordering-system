package com.food.ordering.system.service.domain.ports.output.message.publisher.restaurantapprovel;

import com.food.ordering.system.domain.valueObject.OrderApprovalStatus;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;

import java.util.function.BiConsumer;

public interface RestaurantApprovalRequestMessagePublisher {
    void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage,
                 BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback);
}
