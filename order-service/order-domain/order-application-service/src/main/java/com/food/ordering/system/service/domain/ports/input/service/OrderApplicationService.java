package com.food.ordering.system.service.domain.ports.input.service;

import com.food.ordering.system.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.service.domain.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;

public interface OrderApplicationService {
    //we add @valid cause we added some validation annotations in the dto
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
