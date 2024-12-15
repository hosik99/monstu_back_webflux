package com.icetea.monstu_back.config.log;

import com.icetea.monstu_back.handler.log.ErrorLogHandler;
import com.icetea.monstu_back.handler.log.PostsLogHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ErrorLogConfig {
    @Bean
    public RouterFunction<ServerResponse> ErrorLogRouter(ErrorLogHandler handler) {
        return RouterFunctions.nest(path("/err/log"),
                RouterFunctions.route(GET("/{page}/{size}").and(accept(MediaType.APPLICATION_JSON)), handler::getErrorLog)
        );
    }
}
