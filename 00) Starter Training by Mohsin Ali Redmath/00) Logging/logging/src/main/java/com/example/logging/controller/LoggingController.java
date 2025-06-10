package com.example.logging.controller;

import com.example.logging.data.LogEntity;
import com.example.logging.service.LoggingService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "LoggingControllerBeanName")
@RequestMapping("api/v1/logging")

public class LoggingController {

    private final LoggingService loggingService;

    public LoggingController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping
    ResponseEntity<List<LogEntity>> getLogging() {
        List<LogEntity> logEntities = loggingService.getLogger();
        return logEntities != null ? ResponseEntity.ok(logEntities) : ResponseEntity.notFound().build();
    }

}
