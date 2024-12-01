package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.PostLog;
import com.icetea.monstu_back.model.Posts;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PostLogRepository extends ReactiveMongoRepository<PostLog, String> {

    Flux<PostLog> findTop5ByOrderByViewCountDesc();
}
