package com.icetea.monstu_back.handler;

import com.icetea.monstu_back.dto.PostsPostLogDTO;
import com.icetea.monstu_back.model.PostLog;
import com.icetea.monstu_back.model.Posts;
import com.icetea.monstu_back.repository.PostLogRepository;
import com.icetea.monstu_back.repository.PostsRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PostsHandler {

    private final PostsRepository postsRps;
    private final PostLogRepository postLogRps;

    public PostsHandler(PostsRepository postsRps,PostLogRepository postLogRps) {
        this.postsRps = postsRps;
        this.postLogRps = postLogRps;
    }

    // Get All Posts
    public Mono<ServerResponse> getAllPosts(ServerRequest request) {
        Flux<Posts> postsFlux = postsRps.findAll();
        return ServerResponse.ok().body( postsFlux, Posts.class );
    }

    // Get Top5 Posts View Count
    public Mono<ServerResponse> getTopView(ServerRequest request) {
        Flux<PostLog> postLogsFlux = postLogRps.findTop5ByOrderByViewCountDesc();
        return ServerResponse.ok().body( postLogsFlux, Posts.class );

    }

}
