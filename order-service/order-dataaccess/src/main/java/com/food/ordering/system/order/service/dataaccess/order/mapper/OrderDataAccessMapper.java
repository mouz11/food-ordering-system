package com.food.ordering.system.order.service.dataaccess.order.mapper;

import com.food.ordering.system.domain.valueObject.*;
import com.food.ordering.system.order.service.dataaccess.order.enitity.OrderAddressEntity;
import com.food.ordering.system.order.service.dataaccess.order.enitity.OrderEntity;
import com.food.ordering.system.order.service.dataaccess.order.enitity.OrderItemEntity;
import com.food.ordering.system.service.domain.entity.Order;
import com.food.ordering.system.service.domain.entity.OrderItem;
import com.food.ordering.system.service.domain.entity.Product;
import com.food.ordering.system.service.domain.valueObject.OrderItemId;
import com.food.ordering.system.service.domain.valueObject.StreetAddress;
import com.food.ordering.system.service.domain.valueObject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDataAccessMapper {
    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .price(order.getPrice().getAmount())
                .items(orderItemsToOrderItemEntities(order.getItems()))
                .orderStatus(order.getOrderStatus())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages() != null ?
                        String.join(",", order.getFailureMessages()) : "")
                .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));

        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .id(new OrderId(orderEntity.getId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                .price(new Money(orderEntity.getPrice()))
                .items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessages(orderEntity.getFailureMessages().isEmpty() ?
                        new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(orderEntity
                                .getFailureMessages()
                                .split(","))))
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream()
                .map(item ->
                OrderItem.builder()
                        .id(new OrderItemId(item.getId()))
                        .product(new Product(new ProductId(item.getProductId())))
                        .price(new Money(item.getPrice()))
                        .quantity(item.getQuantity())
                        .subTotal(new Money(item.getSubTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity());
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream().map(item ->
                OrderItemEntity.builder()
                        .id(item.getId().getValue())
                        .productId(item.getProduct().getId().getValue())
                        .price(item.getPrice().getAmount())
                        .quantity(item.getQuantity())
                        .subTotal(item.getSubTotal().getAmount())
                        .build())
                .collect(Collectors.toList());

    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        return OrderAddressEntity.builder()
                .id(deliveryAddress.getID())
                .street(deliveryAddress.getStreet())
                .city(deliveryAddress.getCity())
                .postalCode(deliveryAddress.getPostalCode())
                .build();
    }
}
