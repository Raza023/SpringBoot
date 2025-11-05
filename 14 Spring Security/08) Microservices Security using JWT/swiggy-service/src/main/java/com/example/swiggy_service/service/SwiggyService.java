package com.example.swiggy_service.service;

import com.example.swiggy_service.dto.OrderResponseDTO;
import com.example.swiggy_service.restclient.RestaurantServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SwiggyService {

    private final RestaurantServiceClient restaurantServiceClient;

    public String fetchGreeting() {
        return "Assalam o Alaikum! Hi from Swiggy service.";
    }

    public OrderResponseDTO fetchOrderStatus(String orderId) {
        return restaurantServiceClient.fetchOrderStatus(orderId);
    }
}
