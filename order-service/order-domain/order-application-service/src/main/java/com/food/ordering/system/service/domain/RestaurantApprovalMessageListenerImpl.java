package com.food.ordering.system.service.domain;

import com.food.ordering.system.service.domain.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.service.domain.event.OrderCanceledEvent;
import com.food.ordering.system.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalMessageListener;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Valid
public class RestaurantApprovalMessageListenerImpl implements RestaurantApprovalMessageListener {
    private final OrderApprovalSaga orderApprovalSaga;

    public RestaurantApprovalMessageListenerImpl(OrderApprovalSaga orderApprovalSaga) {
        this.orderApprovalSaga = orderApprovalSaga;
    }

    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {
        orderApprovalSaga.process(restaurantApprovalResponse);
        log.info("Order is approved for order with id: {}", restaurantApprovalResponse.getOrderId());
    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {
        orderApprovalSaga.rollback(restaurantApprovalResponse);
        log.info("Order approval Saga rollback operation is completed for order with id: {} with failureMessages: {}",
                restaurantApprovalResponse.getOrderId(),
                String.join(" -- ", restaurantApprovalResponse.getFailureMessages()));
    }
}
