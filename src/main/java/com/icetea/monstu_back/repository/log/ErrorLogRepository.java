package com.icetea.monstu_back.repository.log;

import com.icetea.monstu_back.model.log.ErrorLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ErrorLogRepository extends ReactiveMongoRepository<ErrorLog, String> {

}
