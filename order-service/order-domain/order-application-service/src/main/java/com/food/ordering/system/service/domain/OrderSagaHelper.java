package com.food.ordering.system.service.domain;

import com.food.ordering.system.domain.valueObject.OrderId;
import com.food.ordering.system.service.domain.entity.Order;
import com.food.ordering.system.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.service.domain.ports.output.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderSagaHelper {
    private final OrderRepository orderRepository;

    public OrderSagaHelper(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    Order findOrder(String orderId) {
        Optional<Order> orderResult = orderRepository.findById(new OrderId(UUID.fromString(orderId)));
        if (orderResult.isEmpty()) {
            log.error("order with id: {} could not be found", orderId);
            throw new OrderNotFoundException("order with id: "
                    + orderId
                    + " could not be found");
        }
        return orderResult.get();
    }
    void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
