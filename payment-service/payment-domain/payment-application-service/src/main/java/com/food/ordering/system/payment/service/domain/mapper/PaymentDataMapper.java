package com.food.ordering.system.payment.service.domain.mapper;

import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.domain.valueObject.Money;
import com.food.ordering.system.domain.valueObject.OrderId;
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {
    public Payment paymentRequestToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .customerId(new CustomerId(UUID.fromString(paymentRequest.getCustomerId())))
                .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }
}
