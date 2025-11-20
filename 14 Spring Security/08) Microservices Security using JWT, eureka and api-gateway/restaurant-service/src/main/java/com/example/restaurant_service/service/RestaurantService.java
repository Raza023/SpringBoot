package com.example.restaurant_service.service;

import com.example.restaurant_service.dao.RestaurantOrderDAO;
import com.example.restaurant_service.dto.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantOrderDAO restaurantOrderDAO;

    public OrderResponseDTO getOrderStatus(String orderId) {
        return restaurantOrderDAO.getOrderById(orderId);
    }
}
