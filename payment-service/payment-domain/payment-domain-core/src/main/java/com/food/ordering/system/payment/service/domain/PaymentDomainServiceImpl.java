package com.food.ordering.system.payment.service.domain;

import com.food.ordering.system.domain.valueObject.Money;
import com.food.ordering.system.domain.valueObject.PaymentStatus;
import com.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.event.PaymentCanceledEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import com.food.ordering.system.payment.service.domain.valueObject.CreditHistoryId;
import com.food.ordering.system.payment.service.domain.valueObject.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService{
    @Override
    public PaymentEvent validateAndCreatePayment(Payment payment,
                                                 CreditEntry creditEntry,
                                                 List<CreditHistory> creditHistories,
                                                 List<String> failureMessages) {
        payment.validatePayment(failureMessages);
        payment.initializePayment();
        validateCreditEntry(payment, creditEntry, failureMessages);
        subtractCreditEntity(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.CREDIT);
        validateCreditHistory(creditHistories, creditEntry, failureMessages);
        if (failureMessages.isEmpty()) {
            log.info("payment is initiated for id:{}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.COMPLETED);
            return new PaymentCompletedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")));
        } else {
            log.info("Payment initialization failed for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), failureMessages);
        }
    }


    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment,
                                                 CreditEntry creditEntry,
                                                 List<CreditHistory> creditHistories,
                                                 List<String> failureMessages) {
        payment.validatePayment(failureMessages);
        addDebitEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.DEBIT);
        if (failureMessages.isEmpty()) {
            log.info("Payment is canceled for order id: {}",
                    payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.CANCELED);
            return new PaymentCanceledEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")));
        } else {
            log.error("payment canceling has failed for order id:{}",
                    payment.getOrderId().getValue());
            return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), failureMessages);
        }
    }

    private void validateCreditEntry(Payment payment, CreditEntry creditEntry, List<String> failureMessages) {
        if (payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())) {
            log.error("Customer with id:{} doesn't have enough credit for payment",
                    payment.getCustomerId().getValue());
            failureMessages.add("Customer with id="
                    + payment.getCustomerId().getValue()
                    + "doesn't have enough credit for payment!");
        }
    }
    private void subtractCreditEntity(Payment payment, CreditEntry creditEntry) {
        creditEntry.subtractCreditAmount(payment.getPrice());
    }
    private void updateCreditHistory(Payment payment, List<CreditHistory> creditHistories, TransactionType transactionType) {
        creditHistories.add(CreditHistory.builder()
                        .creditHistoryId(new CreditHistoryId(UUID.randomUUID()))
                        .customerId(payment.getCustomerId())
                        .amount(payment.getPrice())
                        .transactionType(transactionType)
                .build());
    }
    private void validateCreditHistory(List<CreditHistory> creditHistories, CreditEntry creditEntry, List<String> failureMessages) {
        Money totalCreditHistory = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT);
        Money totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT);
        if (totalCreditHistory.isGreaterThan(totalDebitHistory)) {
            log.error("Customer with id:{} doesn't have enough credit according to the credit history",
                    creditEntry.getCustomerId().getValue());
            failureMessages.add("Customer with id="
                    + creditEntry.getCustomerId().getValue()
                    +"doesn't have enough credit according to the credit history!");
        }
        if (!totalDebitHistory.subtract(totalCreditHistory).equals(creditEntry.getTotalCreditAmount())) {
            log.error("Credit history total is not equal to current credit for customer id: {}",
                    creditEntry.getCustomerId().getValue());
            failureMessages.add("Credit history total is not equal to current credit for customer id="
                    + creditEntry.getCustomerId().getValue()
                   + "!");
        }
    }

    private static Money getTotalHistoryAmount(List<CreditHistory> creditHistories, TransactionType transactionType) {
        return creditHistories.stream()
                .filter(creditHistory -> creditHistory.getTransactionType() == transactionType)
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add);
    }
    private void addDebitEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.addCreditAmount(payment.getPrice());
    }
}
