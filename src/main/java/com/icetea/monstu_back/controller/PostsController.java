package com.icetea.monstu_back.controller;

import com.icetea.monstu_back.dto.PostCreateRequest;
import com.icetea.monstu_back.model.Posts;
import com.icetea.monstu_back.service.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) { this.postsService = postsService; }

    @PostMapping("/save")
    public Mono<ResponseEntity<Boolean>> save(@RequestBody PostCreateRequest postCreateRequest) {
        System.out.println(postCreateRequest.getFormWordData().getOri().size());
        System.out.println(postCreateRequest.getFormWordData().getTran().size());

        System.out.println(postCreateRequest.getFormSenData().getOri().size());
        System.out.println(postCreateRequest.getFormSenData().getTran().size());

        return postsService.save(postCreateRequest)
                .map(success -> {
                    if (success) {
                        return ResponseEntity.status(HttpStatus.CREATED).body(true);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); // 실패 시 500 반환
                    }
                });
    }
}
