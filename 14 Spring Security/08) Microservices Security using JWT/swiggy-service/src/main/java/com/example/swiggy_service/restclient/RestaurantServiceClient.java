package com.example.swiggy_service.restclient;

import com.example.swiggy_service.dto.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestaurantServiceClient {

    private final RestTemplate restTemplate;

    public OrderResponseDTO fetchOrderStatus(String orderId) {
        return restTemplate.getForObject("http://RESTAURANT-SERVICE/restaurant/order/status/" + orderId,
                OrderResponseDTO.class);
    }
}
