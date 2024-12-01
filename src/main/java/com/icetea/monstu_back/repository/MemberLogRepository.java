package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.MemberLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface MemberLogRepository extends ReactiveMongoRepository<MemberLog, String> {

    Mono<MemberLog> findByMemberId(Long memberId);

}
