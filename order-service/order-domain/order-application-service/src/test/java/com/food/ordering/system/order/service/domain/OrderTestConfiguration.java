package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.service.domain.OrderDomainService;
import com.food.ordering.system.service.domain.OrderDomainServiceImpl;
import com.food.ordering.system.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import com.food.ordering.system.service.domain.ports.output.message.publisher.restaurantapprovel.RestaurantApprovalRequestMessagePublisher;
import com.food.ordering.system.service.domain.ports.output.repository.*;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderTestConfiguration {
    @Bean
    public PaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(PaymentRequestMessagePublisher.class);
    }
    @Bean
    public RestaurantApprovalRequestMessagePublisher orderCanceledPaymentRequestPublisher() {
        return Mockito.mock(RestaurantApprovalRequestMessagePublisher.class);
    }
    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }
    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }
    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }
    @Bean
    public PaymentOutboxRepository paymentOutboxRepository() {
        return Mockito.mock(PaymentOutboxRepository.class);
    }@Bean
    public ApprovalOutboxRepository approvalOutboxRepository() {
        return Mockito.mock(ApprovalOutboxRepository.class);
    }
    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
