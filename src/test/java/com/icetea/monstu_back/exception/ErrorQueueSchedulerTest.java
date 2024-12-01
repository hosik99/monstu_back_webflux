package com.icetea.monstu_back.exception;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.mockito.Mockito.*;

@SpringBootTest
@EnableScheduling
class ErrorQueueSchedulerTest {

    private final ErrorQueue errorQueue = mock(ErrorQueue.class);
    private final ErrorQueueScheduler errorQueueScheduler = new ErrorQueueScheduler(errorQueue);

    @Test
    void flushErrorQueue_shouldCallFlushToDatabase() {
        errorQueueScheduler.flushErrorQueue();

        // verify를 사용하여 flushToDatabase 메서드가 한 번 호출되었는지 확인
        verify(errorQueue, times(1)).flushToDatabase();
    }
}

// ./gradlew test