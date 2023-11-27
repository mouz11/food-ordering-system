package com.food.ordering.system.restaurant.service.domain.ports.output.message.publisher;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;
import org.springframework.stereotype.Component;

@Component
public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {
}
