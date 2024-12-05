package com.icetea.monstu_back.exception;

import com.icetea.monstu_back.model.ErrorLog;
import com.icetea.monstu_back.repository.ErrorLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class ErrorQueueTest {

//    private final ErrorLogRepository errorLogRepository = mock(ErrorLogRepository.class);
//    private final ErrorQueue errorQueue = new ErrorQueue(errorLogRepository);
//
//    @Test
//    void addError_whenBatchSizeReached_shouldFlushToDatabase() {
//        // Given
//        when(errorLogRepository.saveAll(anyList())).thenReturn(Mono.just(new ErrorLog())); // Mock save method
//
//        // Add errors to the queue
//        for (int i = 0; i < 10; i++) {
//            errorQueue.addError(new ErrorLog(String.valueOf(i),500, (long) i,"message: "+i,"/end/point","GET","Hostname", LocalDateTime.now()));
//        }
//
//        // Verify that saveAll was called
//        verify( errorLogRepository, times(1)).saveAll( anyList() );
//    }
}
