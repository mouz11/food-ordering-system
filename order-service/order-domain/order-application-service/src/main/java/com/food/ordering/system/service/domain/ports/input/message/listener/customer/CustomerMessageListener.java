package com.food.ordering.system.service.domain.ports.input.message.listener.customer;

import com.food.ordering.system.service.domain.dto.message.CustomerModel;

public interface CustomerMessageListener {
    void customerCreated(CustomerModel customerModel);
}
