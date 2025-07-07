package com.example.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

// The health() method is called by Spring Boot Actuator when the /actuator/health endpoint is accessed.
// It checks the internet connection by trying to connect to a well-known URL (in this case, Google).
// If the connection is successful, it returns an "up" status; otherwise, it returns a "down" status.

// Our output will be like this:
// "internetHealthMetrics": {
//     "status": "UP",
//     "details": {
//         "success code": "Active Internet Connection"
//     }
// }
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
