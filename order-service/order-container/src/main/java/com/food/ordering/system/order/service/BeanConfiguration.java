package com.food.ordering.system.order.service;

import com.food.ordering.system.service.domain.OrderDomainService;
import com.food.ordering.system.service.domain.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
