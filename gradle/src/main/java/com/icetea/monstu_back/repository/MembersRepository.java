package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.Members;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface MembersRepository extends ReactiveCrudRepository<Members, Long> {

}
