package com.food.ordering.system.restaurant.service.domain.ports.input.listener;

import com.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest;

public interface RestaurantApprovalRequestMessageListener {
    public void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}
