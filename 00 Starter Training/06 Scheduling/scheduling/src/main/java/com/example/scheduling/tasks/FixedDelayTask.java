package com.example.scheduling.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FixedDelayTask {

    // Execute after a delay of 10 seconds
    @Scheduled(fixedDelay = 10000)
    public void performFixedDelayTask() {
        log.info("This is my task of fixed delay, that is executed after a delay of 10 seconds.");
    }
}