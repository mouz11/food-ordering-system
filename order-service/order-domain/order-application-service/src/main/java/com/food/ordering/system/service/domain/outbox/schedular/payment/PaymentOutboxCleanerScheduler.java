package com.food.ordering.system.service.domain.outbox.schedular.payment;

import com.food.ordering.system.outbox.OutboxScheduler;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.saga.SagaStatus;
import com.food.ordering.system.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PaymentOutboxCleanerScheduler implements OutboxScheduler {
   private final PaymentOutboxHelper paymentOutboxHelper;

    public PaymentOutboxCleanerScheduler(PaymentOutboxHelper paymentOutboxHelper) {
        this.paymentOutboxHelper = paymentOutboxHelper;
    }

    @Override
    @Scheduled(cron = "@midnight")
    public void processOutboxMessage() {
        Optional<List<OrderPaymentOutboxMessage>> outboxMessages = paymentOutboxHelper.getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
                OutboxStatus.COMPLETED,
                SagaStatus.COMPENSATED,
                SagaStatus.FAILED,
                SagaStatus.SUCCEEDED
        );
        if (outboxMessages.isPresent()) {
            List<OrderPaymentOutboxMessage> orderPaymentOutboxMessages = outboxMessages.get();
            log.info("Received {} OrderPaymentOutboxMessage fro clean-up. The payloads: {}",
                    orderPaymentOutboxMessages.size(),
                    orderPaymentOutboxMessages.stream()
                            .map(OrderPaymentOutboxMessage::getPayload)
                            .collect(Collectors.joining("/n")));
            paymentOutboxHelper.deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus.COMPLETED,
                    SagaStatus.FAILED,
                    SagaStatus.SUCCEEDED,
                    SagaStatus.COMPENSATED);
            log.info("{} OrderPaymentOutboxMessage deleted!", orderPaymentOutboxMessages.size());
        }
    }
}
