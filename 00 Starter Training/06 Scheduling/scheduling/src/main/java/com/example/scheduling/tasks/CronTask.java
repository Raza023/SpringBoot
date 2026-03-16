package com.example.scheduling.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CronTask {

    // Execute at 12:00 PM every day
    @Scheduled(cron = "0 0 12 * * ?")
    public void performCronTask() {
        log.info("This is my task of initial delay, that is executed at 12:00 PM every day");
    }
}