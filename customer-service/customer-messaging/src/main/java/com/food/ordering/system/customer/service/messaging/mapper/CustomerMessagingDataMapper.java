package com.food.ordering.system.customer.service.messaging.mapper;

import com.food.ordering.system.customer.service.domain.event.CustomerCreatedEvent;
import com.food.ordering.system.kafka.order.avro.model.CustomerAvroModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerMessagingDataMapper {

    public CustomerAvroModel cistomerCreatedEventToCustomerAvroModel(CustomerCreatedEvent
                                                                               customerCreatedEvent) {
        return CustomerAvroModel.newBuilder()
                .setId(customerCreatedEvent.getCustomer().getId().getValue().toString())
                .setUsername(customerCreatedEvent.getCustomer().getUsername())
                .setFirstname(customerCreatedEvent.getCustomer().getFirstName())
                .setLastname(customerCreatedEvent.getCustomer().getLastName())
                .build();
    }
}
