package com.example.amazon_shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    private RestTemplate template;

    @GetMapping("/eureka-amazon-payment/{price}")
    public String invokePaymentServiceWithEurekaServer(@PathVariable Integer price) {
        String url = "http://PAYMENT-SERVICE/payment-provider/payNow/" + price;
        return template.getForObject(url, String.class);
    }

    @GetMapping("/amazon-payment/{price}")
    public String invokePaymentServiceWithoutEurekaService(@PathVariable Integer price) {
        //following request will be directly from payment service.
        String url = "http://localhost:8888/payment-provider/payNow/" + price;
        return template.getForObject(url, String.class);
    }
}
