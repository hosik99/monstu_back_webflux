package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.words.WordsEngKo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WordsEngKoRepository extends ReactiveMongoRepository<WordsEngKo, String> {
}
