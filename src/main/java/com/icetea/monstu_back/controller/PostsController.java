package com.icetea.monstu_back.controller;

import com.icetea.monstu_back.model.Posts;
import com.icetea.monstu_back.service.PostsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) { this.postsService = postsService; }

}
