package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.Posts;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface PostsRepository extends ReactiveCrudRepository<Posts, Long> {

//    @Query("SELECT id, title FROM posts where id = :id")/*
//    Mono<SyncPostDTO> findIdAndTitleById(Long id);*/

}
