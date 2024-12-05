package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.SentencesEngKo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SentencesEngKoRepository extends ReactiveMongoRepository<SentencesEngKo, String> {
}
