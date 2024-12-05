package com.icetea.monstu_back.repository.log;

import com.icetea.monstu_back.model.log.PostLog;
import com.icetea.monstu_back.repository.custom.PageableCustomRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PostLogRepository extends ReactiveMongoRepository<PostLog, String> {

}
