package com.example.scheduling.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitialDelayTask {

    // Start after 30 seconds, then execute every 60 seconds
    @Scheduled(initialDelay = 30000, fixedRate = 60000)
    public void performInitialDelayTask() {
        log.info("This is my task of initial delay, that starts after 30 seconds, then executes every 60 seconds");
    }
}
