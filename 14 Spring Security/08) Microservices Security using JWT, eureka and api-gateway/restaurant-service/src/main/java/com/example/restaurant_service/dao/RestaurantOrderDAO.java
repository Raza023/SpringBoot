package com.example.restaurant_service.dao;

import com.example.restaurant_service.dto.OrderResponseDTO;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOrderDAO {

    public OrderResponseDTO getOrderById(String orderId) {
        return getStringOrderResponseDTOMap().get(orderId);
    }

    private static Map<String, OrderResponseDTO> getStringOrderResponseDTOMap() {
        Map<String, OrderResponseDTO> orderResponseDTOMap = new HashMap<>();
        orderResponseDTOMap.put("orderId001",
                new OrderResponseDTO("orderId001", "Karachi Biryani", 1, 200, new Date(), "READY", 2));
        orderResponseDTOMap.put("orderId002",
                new OrderResponseDTO("orderId002", "Mutton Karahi", 1, 400, new Date(), "Delivered", 0));
        orderResponseDTOMap.put("orderId003",
                new OrderResponseDTO("orderId003", "Bong Paye", 1, 300, new Date(), "READY", 2));
        orderResponseDTOMap.put("orderId004",
                new OrderResponseDTO("orderId004", "Achar Gosht", 1, 250, new Date(), "Preparing", 45));
        return orderResponseDTOMap;
    }

}
