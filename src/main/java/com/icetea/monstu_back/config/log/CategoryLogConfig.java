package com.icetea.monstu_back.config.log;

import com.icetea.monstu_back.handler.log.CategoryLogHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class CategoryLogConfig {
    @Bean
    public RouterFunction<ServerResponse> CategoryLogRouter(CategoryLogHandler handler) {
        return RouterFunctions.nest(path("/cate/log"),
                RouterFunctions.route(GET("/{page}/{size}").and(accept(MediaType.APPLICATION_JSON)), handler::getCategoryLog)
        );
    }
}
