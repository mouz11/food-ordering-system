package com.food.ordering.system.restaurant.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueObject.Money;
import com.food.ordering.system.domain.valueObject.OrderApprovalStatus;
import com.food.ordering.system.domain.valueObject.OrderStatus;
import com.food.ordering.system.domain.valueObject.RestaurantId;
import com.food.ordering.system.restaurant.service.domain.valueObject.OrderApprovalId;

import java.util.List;
import java.util.UUID;

public class Restaurant extends AggregateRoot<RestaurantId> {
    private OrderApproval orderApproval;
    private boolean active;
    private final OrderDetails orderDetails;

    public void validateOrder(List<String> failureMessages) {
        if (orderDetails.getOrderStatus() != OrderStatus.PAID) {
            failureMessages.add("Payment is not completed for order id: {}" + orderDetails.getId().getValue());
        }
        Money totalAmount = orderDetails.getProducts().stream()
                .map(product -> {
                    if (!product.isAvailable()) {
                        failureMessages.add("Product with id "
                                + product.getId().getValue() + " is not available");
                    }
                    return product.getPrice().multiply(product.getQuantity());
                }).reduce(Money.ZERO, Money::add);
        if (!totalAmount.equals(orderDetails.getTotalPrice())) {
            failureMessages.add("Price total is not correct for order: " + orderDetails.getId().getValue());
        }
    }
    public void constructOrderApproval(OrderApprovalStatus orderApprovalStatus) {
        this.orderApproval = OrderApproval.builder()
                .orderApprovalId(new OrderApprovalId(UUID.randomUUID()))
                .orderId(this.orderDetails.getId())
                .restaurantId(this.getId())
                .orderApprovalStatus(orderApprovalStatus)
                .build();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public OrderApproval getOrderApproval() {
        return orderApproval;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    private Restaurant(Builder builder) {
        setId(builder.restaurantId);
        orderApproval = builder.orderApproval;
        active = builder.active;
        orderDetails = builder.orderDetails;
    }
    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private RestaurantId restaurantId;
        private OrderApproval orderApproval;
        private boolean active;
        private OrderDetails orderDetails;

        private Builder() {
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder orderApproval(OrderApproval val) {
            orderApproval = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Builder orderDetails(OrderDetails val) {
            orderDetails = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
