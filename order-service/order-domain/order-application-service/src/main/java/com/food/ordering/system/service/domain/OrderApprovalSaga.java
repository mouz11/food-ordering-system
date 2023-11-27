package com.food.ordering.system.service.domain;

import com.food.ordering.system.domain.event.EmptyEvent;
import com.food.ordering.system.saga.SagaStep;
import com.food.ordering.system.service.domain.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.service.domain.entity.Order;
import com.food.ordering.system.service.domain.event.OrderCanceledEvent;
import com.food.ordering.system.service.domain.ports.output.message.publisher.payment.OrderCanceledPaymentRequestPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class OrderApprovalSaga implements SagaStep<RestaurantApprovalResponse, EmptyEvent, OrderCanceledEvent> {
    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final OrderCanceledPaymentRequestPublisher orderCanceledPaymentRequestPublisher;

    public OrderApprovalSaga(OrderDomainService orderDomainService,
                             OrderSagaHelper orderSagaHelper,
                             OrderCanceledPaymentRequestPublisher orderCanceledPaymentRequestPublisher) {
        this.orderDomainService = orderDomainService;
        this.orderSagaHelper = orderSagaHelper;
        this.orderCanceledPaymentRequestPublisher = orderCanceledPaymentRequestPublisher;
    }

    @Override
    @Transactional
    public EmptyEvent process(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("approving order with id: {}", restaurantApprovalResponse.getOrderId());
        Order order = orderSagaHelper.findOrder(UUID.fromString(restaurantApprovalResponse.getOrderId()));
        orderDomainService.approveOrder(order);
        orderSagaHelper.saveOrder(order);
        log.info("Order with id: {} is approved", order.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public OrderCanceledEvent rollback(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("Canceling order with id: {}", restaurantApprovalResponse.getOrderId());
        Order order = orderSagaHelper.findOrder(UUID.fromString(restaurantApprovalResponse.getOrderId()));
        OrderCanceledEvent orderCanceledEvent = orderDomainService.cancelOrderPayment(order, restaurantApprovalResponse.getFailureMessages());
        orderSagaHelper.saveOrder(order);
        log.info("Order Canceling with id: {}", order.getId().getValue());
        return orderCanceledEvent;
    }
}
