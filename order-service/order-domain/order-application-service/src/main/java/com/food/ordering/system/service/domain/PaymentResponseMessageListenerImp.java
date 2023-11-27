package com.food.ordering.system.service.domain;

import com.food.ordering.system.service.domain.dto.message.PaymentResponse;
import com.food.ordering.system.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import com.food.ordering.system.service.domain.ports.output.message.publisher.restaurantapprovel.OrderPaidRestaurantRequestPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
public class PaymentResponseMessageListenerImp implements PaymentResponseMessageListener {
    private final OrderPaymentSaga orderPaymentSaga;
    private final OrderPaidRestaurantRequestPublisher orderPaidRestaurantRequestPublisher;

    public PaymentResponseMessageListenerImp(OrderPaymentSaga orderPaymentSaga, OrderPaidRestaurantRequestPublisher orderPaidRestaurantRequestPublisher) {
        this.orderPaymentSaga = orderPaymentSaga;
        this.orderPaidRestaurantRequestPublisher = orderPaidRestaurantRequestPublisher;
    }

    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {
        OrderPaidEvent orderPaidEvent = orderPaymentSaga.process(paymentResponse);
        log.info("Publishing OrderPaidEvent for order id: {}", paymentResponse.getOrderId());
        orderPaidRestaurantRequestPublisher.publish(orderPaidEvent);
    }

    @Override
    public void paymentCanceled(PaymentResponse paymentResponse) {
        orderPaymentSaga.rollback(paymentResponse);
        log.info("Order is rolledBack for order id:{} with failure message: {}",
                paymentResponse.getOrderId(),
                String.join(" -- ", paymentResponse.getFailureMessages()));
    }
}
