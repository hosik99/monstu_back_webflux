package com.icetea.monstu_back.exception;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class GlobalExceptionHandlerTest {

    private final ExceptionManager exceptionManager = mock(ExceptionManager.class);
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler(exceptionManager);
    private final ServerWebExchange exchange = mock(ServerWebExchange.class);

    @Test
    void handleResponseStatusException_shouldCallAddQueueAndWriteErrorResponse() {
        // Given
        ResponseStatusException ex = new ResponseStatusException(BAD_REQUEST, "Bad Request");

        // When
        globalExceptionHandler.handle(exchange, ex);

        // Then
        verify(exceptionManager, times(1)).addQueue(eq(exchange), eq(ex));
        verify(exceptionManager, times(1)).writeErrorResponse(eq(exchange), eq(BAD_REQUEST), isNull());
    }
}
