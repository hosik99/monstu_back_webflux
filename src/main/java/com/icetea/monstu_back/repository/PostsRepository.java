package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.dto.SyncPostDTO;
import com.icetea.monstu_back.model.Posts;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface PostsRepository extends ReactiveCrudRepository<Posts, Long> {

//    @Query("SELECT id, title FROM posts where id = :id")/*
//    Mono<SyncPostDTO> findIdAndTitleById(Long id);*/

}
