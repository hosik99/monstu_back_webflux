//package com.icetea.monstu_back.exception;
//
//import com.icetea.monstu_back.model.ErrorLog;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.springframework.http.HttpStatus.BAD_REQUEST;
//
//class ExceptionManagerTest {
//
//    private final ErrorQueue errorQueue = Mockito.mock(ErrorQueue.class);  // mock 객체
//    private final ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class);  // mock 객체
//    private final ExceptionManager exceptionManager = new ExceptionManager(errorQueue);
//
//    @Test
//    void addQueue_shouldAddErrorLogToQueue() {
//        // Given
//        ResponseStatusException ex = new ResponseStatusException(BAD_REQUEST, "Bad Request");
//
//        // When
//        exceptionManager.addQueue(exchange, ex);
//
//        // Then
//        ArgumentCaptor<ErrorLog> errorLogCaptor = ArgumentCaptor.forClass(ErrorLog.class);
//        verify( errorQueue, times(1)).addError(errorLogCaptor.capture());
//
//        ErrorLog capturedErrorLog = errorLogCaptor.getValue();
//        // Validate the captured error log
//        assertEquals(400, capturedErrorLog.getCode());  // assertEquals를 사용
//        assertEquals("Bad Request", capturedErrorLog.getMessage());  // assertEquals를 사용
//    }
//}
