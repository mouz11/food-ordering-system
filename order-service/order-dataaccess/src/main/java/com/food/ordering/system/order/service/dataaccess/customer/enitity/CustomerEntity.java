package com.food.ordering.system.order.service.dataaccess.customer.enitity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerEntity {
    @Id
    private UUID id;
    private String username;
    private String firstname;
    private String lastname;
}
