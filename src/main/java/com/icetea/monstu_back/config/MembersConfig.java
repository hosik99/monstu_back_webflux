package com.icetea.monstu_back.config;

import com.icetea.monstu_back.handler.MembersHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class MembersConfig {
    @Bean
    public RouterFunction<ServerResponse> MembersRoute(MembersHandler handler){
        return RouterFunctions
                .route(GET("/members").and(accept(MediaType.APPLICATION_JSON)), handler::getAllMembers);

    }


}
