package com.food.ordering.system.service.domain.ports.input.message.listener.restaurantapproval;

import com.food.ordering.system.service.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalMessageListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
