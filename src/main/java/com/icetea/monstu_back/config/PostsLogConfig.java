package com.icetea.monstu_back.config;

import com.icetea.monstu_back.handler.PostsHandler;
import com.icetea.monstu_back.handler.PostsLogHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PostsLogConfig {
    @Bean
    public RouterFunction<ServerResponse> postsLogRouter(PostsLogHandler handler) {
        return RouterFunctions.nest(path("/log"),
                RouterFunctions.route(GET("/{page}/{size}").and(accept(MediaType.APPLICATION_JSON)), handler::getPostLog)
        );
    }
}
