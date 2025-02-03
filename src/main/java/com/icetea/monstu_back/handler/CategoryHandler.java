package com.icetea.monstu_back.handler;

import com.icetea.monstu_back.dto.PostsDTO;
import com.icetea.monstu_back.model.PostCategory;
import com.icetea.monstu_back.repository.PostCategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CategoryHandler {

    private final PostCategoryRepository postCategoryRps;

    public CategoryHandler(PostCategoryRepository postCategoryRps) {
        this.postCategoryRps = postCategoryRps;
    }

    public Mono<ServerResponse> getAllPostCate(ServerRequest request){
        Flux<PostCategory> postCategoryFlux = postCategoryRps.findAll();
        return postCategoryFlux.hasElements()
                .flatMap(hasElements -> hasElements
                        ? ServerResponse.ok().body(postCategoryFlux, PostCategory.class)
                        : ServerResponse.noContent().build());
    }
}
