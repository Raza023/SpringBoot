package com.example.logging.service;

import com.example.logging.data.LogEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
// can use this annotation instead of line 14. (static final Logger log = LoggerFactory.getLogger(LoggingService.class);)
public class LoggingService {

    // static final Logger log = LoggerFactory.getLogger(LoggingService.class);

    public List<LogEntity> getLogger() {
        List<LogEntity> logEntities = getLogEntities();
        // Logger log = LoggerFactory.getLogger(getClass());
        log.debug("Data I have got in logging is: [{}]", logEntities);
        return logEntities;
    }

    private List<LogEntity> getLogEntities() {
        LogEntity logEntity = new LogEntity();
        logEntity.setId(1);
        logEntity.setLoggingTitle("test 1");
        LogEntity logEntity2 = new LogEntity();
        logEntity2.setId(2);
        logEntity2.setLoggingTitle("test 2");
        List<LogEntity> logEntities = new ArrayList<>();
        logEntities.add(logEntity);
        logEntities.add(logEntity2);
        return logEntities;
    }

}
