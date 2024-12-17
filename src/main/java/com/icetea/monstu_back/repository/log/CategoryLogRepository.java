package com.icetea.monstu_back.repository.log;

import com.icetea.monstu_back.model.log.CategoryLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryLogRepository extends ReactiveMongoRepository<CategoryLog, String> {
}
