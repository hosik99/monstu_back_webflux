package com.icetea.monstu_back.repository.log;

import com.icetea.monstu_back.model.log.MemberLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface MemberLogRepository extends ReactiveMongoRepository<MemberLog, String> {

    Mono<MemberLog> findByMemberId(Long memberId);

}
