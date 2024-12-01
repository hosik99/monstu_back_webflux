package com.icetea.monstu_back.exception;

import com.icetea.monstu_back.model.ErrorLog;
import com.icetea.monstu_back.repository.ErrorLogRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ErrorQueue {

    private final ErrorLogRepository errorLogRepository;

    private final Queue<ErrorLog> errorQueue = new ConcurrentLinkedQueue<>();   // 동기화된 큐
    private final int batchSize = 10; // 배치 크기

    public ErrorQueue(ErrorLogRepository errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }

    public void addError(ErrorLog error) {
        errorQueue.add(error);
        if (errorQueue.size() >= batchSize) {
            flushToDatabase();
        }
    }

    public void flushToDatabase() {
        if (!errorQueue.isEmpty()) {
            List<ErrorLog> batch = new ArrayList<>();
            while (!errorQueue.isEmpty() && batch.size() < batchSize) {
                batch.add(errorQueue.poll());
            }
            saveToDatabase(batch).subscribe();
        }
    }

    private Mono<Void> saveToDatabase(List<ErrorLog> errors) {
        return errorLogRepository.saveAll(errors)
                .then() // 결과를 Mono<Void>로 변환
                .doOnSuccess(unused -> System.out.println("DB 저장 완료"))
                .doOnError(error -> System.err.println("DB 저장 실패: " + error.getMessage()));
    }


}

