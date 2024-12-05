package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.PostLog;
import com.icetea.monstu_back.model.Posts;
import com.icetea.monstu_back.repository.custom.CustomPostLogRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PostLogRepository extends ReactiveMongoRepository<PostLog, String>, CustomPostLogRepository {

}
