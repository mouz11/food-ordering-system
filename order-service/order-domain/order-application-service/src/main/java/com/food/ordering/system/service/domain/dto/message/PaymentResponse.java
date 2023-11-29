package com.food.ordering.system.service.domain.dto.message;

import com.food.ordering.system.domain.valueObject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String sagaId;
    private String orderId;
    private String paymentId;
    private String customerId;
    private BigDecimal price;
    private Long createdAt;
    private PaymentStatus paymentStatus;
    private List<String> failureMessages;

}
