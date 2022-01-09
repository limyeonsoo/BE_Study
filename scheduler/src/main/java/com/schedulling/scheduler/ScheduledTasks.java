package com.schedulling.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelay = 1000)
    @Async
    public void reportCurrentTime() throws InterruptedException {
        log.error("before sleep time is now {}", dateFormat.format(new Date()));
        Thread.sleep(6000);
        log.info("after sleep time is now {}", dateFormat.format(new Date()));
        log.info("\n");
    }
}
