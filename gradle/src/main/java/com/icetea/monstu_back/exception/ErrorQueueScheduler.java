package com.icetea.monstu_back.exception;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ErrorQueueScheduler {

    private final ErrorQueue errorQueue;

    public ErrorQueueScheduler(ErrorQueue errorQueue) {
        this.errorQueue = errorQueue;
    }

    @Scheduled(fixedRate = 60000) // 60초마다 실행
    public void flushErrorQueue() {
        errorQueue.flushToDatabase();
    }
}
