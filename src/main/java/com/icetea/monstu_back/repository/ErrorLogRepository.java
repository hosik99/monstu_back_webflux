package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.ErrorLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ErrorLogRepository extends ReactiveMongoRepository<ErrorLog, String> {

}
