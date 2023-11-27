package com.food.ordering.system.order.service.dataaccess.customer.mapper;

import com.food.ordering.system.domain.valueObject.CustomerId;
import com.food.ordering.system.order.service.dataaccess.customer.enitity.CustomerEntity;
import com.food.ordering.system.service.domain.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {
    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
