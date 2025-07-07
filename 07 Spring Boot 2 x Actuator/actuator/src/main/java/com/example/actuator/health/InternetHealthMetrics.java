package com.example.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

@Component
public class InternetHealthMetrics implements HealthIndicator {
    @Override
    public Health health() {
        return checkInternet() == true ? Health.up().withDetail("success code", "Active Internet Connection").build()
                : Health.down().withDetail("error code", "Inactive Internet Connection").build();
    }

    private boolean checkInternet() {
        boolean flag = false;
        try {
            URI uri = new URI("https://www.google.com");
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            connection.connect();
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

}
