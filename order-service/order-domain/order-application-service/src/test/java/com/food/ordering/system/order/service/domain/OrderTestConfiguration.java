package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.service.domain.OrderDomainService;
import com.food.ordering.system.service.domain.OrderDomainServiceImpl;
import com.food.ordering.system.service.domain.ports.output.message.publisher.payment.OrderCanceledPaymentRequestPublisher;
import com.food.ordering.system.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.service.domain.ports.output.message.publisher.restaurantapprovel.OrderPaidRestaurantRequestPublisher;
import com.food.ordering.system.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.service.domain.ports.output.repository.RestaurantRepository;
import com.sun.source.tree.ModuleTree;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderTestConfiguration {
    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }
    @Bean
    public OrderCanceledPaymentRequestPublisher orderCanceledPaymentRequestPublisher() {
        return Mockito.mock(OrderCanceledPaymentRequestPublisher.class);
    }
    @Bean
    public OrderPaidRestaurantRequestPublisher orderPaidRestaurantRequestPublisher() {
        return Mockito.mock(OrderPaidRestaurantRequestPublisher.class);
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
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
