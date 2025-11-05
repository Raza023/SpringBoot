package com.example.restaurant_service.controller;

import com.example.restaurant_service.dto.OrderResponseDTO;
import com.example.restaurant_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/order/status/{orderId}")
    private OrderResponseDTO getOrderStatus(@PathVariable String orderId) {
        return restaurantService.getOrderStatus(orderId);
    }



}
