package com.food.ordering.system.service.domain;

import com.food.ordering.system.service.domain.dto.message.PaymentResponse;
import com.food.ordering.system.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
public class PaymentResponseMessageListenerImp implements PaymentResponseMessageListener {
    private final OrderPaymentSaga orderPaymentSaga;

    public PaymentResponseMessageListenerImp(OrderPaymentSaga orderPaymentSaga) {
        this.orderPaymentSaga = orderPaymentSaga;
    }

    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {
        orderPaymentSaga.process(paymentResponse);
        log.info("Order payment saga operation is completed for order id: {}", paymentResponse.getOrderId());
    }

    @Override
    public void paymentCanceled(PaymentResponse paymentResponse) {
        orderPaymentSaga.rollback(paymentResponse);
        log.info("Order is rolledBack for order id:{} with failure message: {}",
                paymentResponse.getOrderId(),
                String.join(" -- ", paymentResponse.getFailureMessages()));
    }
}
