package com.icetea.monstu_back.config;

import com.icetea.monstu_back.handler.PostsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PostsConfig {
    @Bean
    public RouterFunction<ServerResponse> postsRouter(PostsHandler handler) {
        return RouterFunctions.nest(path("/posts"),
                        RouterFunctions.route(GET("/{page}/{size}").and(accept(MediaType.APPLICATION_JSON)), handler::getAllPosts)
        );
    }


}
