package com.example.scheduling.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FixedRateTask {

    // Execute every 5 seconds
    @Scheduled(fixedRate = 5000)
    public void performFixedRateTask() {
        log.info("This is my task of fixed rate that is executed in every 5 seconds.");
    }
}
