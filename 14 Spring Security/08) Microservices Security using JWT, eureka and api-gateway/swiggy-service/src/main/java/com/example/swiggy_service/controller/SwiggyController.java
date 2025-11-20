package com.example.swiggy_service.controller;

import com.example.swiggy_service.dto.OrderResponseDTO;
import com.example.swiggy_service.service.SwiggyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swiggy")
@RequiredArgsConstructor
public class SwiggyController {

    private final SwiggyService swiggyService;

    @GetMapping("/home")
    public String fetchGreetingMessage() {
        return swiggyService.fetchGreeting();
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO checkOrderStatus(@PathVariable String orderId) {
        return swiggyService.fetchOrderStatus(orderId);
    }
}
