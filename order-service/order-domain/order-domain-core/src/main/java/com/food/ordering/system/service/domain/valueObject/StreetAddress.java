package com.food.ordering.system.service.domain.valueObject;

import java.util.Objects;
import java.util.UUID;

public class StreetAddress {
    private final UUID ID;
    private final String street;
    private final String postalCode;
    private final String city;

    public StreetAddress(UUID ID, String street, String postalCode, String city) {
        this.ID = ID;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public UUID getID() {
        return ID;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetAddress that = (StreetAddress) o;
        return Objects.equals(street, that.street) && Objects.equals(postalCode, that.postalCode) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, postalCode, city);
    }
}
