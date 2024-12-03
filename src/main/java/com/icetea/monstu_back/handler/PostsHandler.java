package com.icetea.monstu_back.handler;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.model.Posts;
import com.icetea.monstu_back.repository.PostsRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PostsHandler {

    private final PostsRepository postsRps;

    public PostsHandler(PostsRepository postsRps) {
        this.postsRps = postsRps;
    }

    // Get All Posts
    public Mono<ServerResponse> getAllPosts(ServerRequest request) {
//        CustomPageableDTO pageableDTO = PageableManager.extract(request);   // page,size 추출

        Flux<Posts> postsFlux = postsRps.findAll();

        return ServerResponse.ok().body( postsFlux, Posts.class );
    }



}
