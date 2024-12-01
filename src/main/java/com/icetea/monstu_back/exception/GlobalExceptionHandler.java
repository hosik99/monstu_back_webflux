package com.icetea.monstu_back.exception;

import com.mongodb.lang.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
public class GlobalExceptionHandler implements WebExceptionHandler {

    private final ExceptionManager exceptionManager;

    public GlobalExceptionHandler(ExceptionManager exceptionManager) {
        this.exceptionManager = exceptionManager;
    }

    @Override
    public @NonNull Mono<Void> handle(@NonNull ServerWebExchange exchange, Throwable ex) {
        return switch (ex) {
            case ResponseStatusException rse -> handleResponseStatusException(exchange, rse);
            case IllegalArgumentException iae -> handleIllegalArgumentException(exchange, 500, iae.getMessage()==null ? "Illegal Argument exception occurred" : iae.getMessage());
            case NullPointerException npe -> handleNullPointerException(exchange, 500, npe.getMessage()==null ? "Null pointer exception occurred" : npe.getMessage());
            default -> handleGenericException(exchange, ex);
        };
    }

    // Response Status Exception
    private Mono<Void> handleResponseStatusException(ServerWebExchange exchange, ResponseStatusException ex) {
        exceptionManager.addQueue(exchange,ex);
        return exceptionManager.writeErrorResponse( exchange, ex.getStatusCode(),null);
    }

    // Illegal Argument Exception
    private Mono<Void> handleIllegalArgumentException(ServerWebExchange exchange, int exCode, String message) {
        exceptionManager.addQueue(exchange,exCode,message);
        return exceptionManager.writeErrorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    // Null Pointer Exception
    private Mono<Void> handleNullPointerException(ServerWebExchange exchange, int exCode, String message) {
        exceptionManager.addQueue(exchange,exCode,message);
        return exceptionManager.writeErrorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    // 기본적인 예외 처리
    private Mono<Void> handleGenericException(ServerWebExchange exchange, Throwable ex) {
        return exceptionManager.writeErrorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error occurred");
    }

}