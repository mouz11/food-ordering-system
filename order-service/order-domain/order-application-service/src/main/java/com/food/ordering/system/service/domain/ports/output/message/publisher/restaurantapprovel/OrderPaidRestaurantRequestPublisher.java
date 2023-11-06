package com.food.ordering.system.service.domain.ports.output.message.publisher.restaurantapprovel;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestPublisher extends DomainEventPublisher<OrderPaidEvent> {
}
