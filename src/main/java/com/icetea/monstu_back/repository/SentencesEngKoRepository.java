package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.words.SentencesEngKo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SentencesEngKoRepository extends ReactiveMongoRepository<SentencesEngKo, String> {
    Mono<SentencesEngKo> findFirstByOrderByPostIdDesc();
}
