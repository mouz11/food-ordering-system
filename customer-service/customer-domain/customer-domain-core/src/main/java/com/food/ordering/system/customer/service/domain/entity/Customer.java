package com.food.ordering.system.customer.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueObject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    private final String username;
    private final String firstname;
    private final String lastname;

    public Customer(CustomerId customerId, String username, String firstName, String lastName) {
        super.setId(customerId);
        this.username = username;
        this.firstname = firstName;
        this.lastname = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }
}


