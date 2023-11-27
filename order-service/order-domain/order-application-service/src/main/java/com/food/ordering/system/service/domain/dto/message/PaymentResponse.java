package com.food.ordering.system.service.domain.dto.message;

import com.food.ordering.system.domain.valueObject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class PaymentResponse {
    private UUID id;
    private UUID sagaId;
    private UUID orderId;
    private UUID paymentId;
    private UUID customerId;
    private BigDecimal price;
    private Long createdAt;
    private PaymentStatus paymentStatus;
    private List<String> failureMessages;

}
