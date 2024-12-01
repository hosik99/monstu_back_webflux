package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.Posts;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface PostsRepository extends ReactiveCrudRepository<Posts, Long> {

}
