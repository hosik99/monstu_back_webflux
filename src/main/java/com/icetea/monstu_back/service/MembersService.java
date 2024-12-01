package com.icetea.monstu_back.service;

import com.icetea.monstu_back.model.Members;
import com.icetea.monstu_back.repository.MembersRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class MembersService {

    private final MembersRepository membersRepository;

    public MembersService(MembersRepository membersRepository) {
        this.membersRepository = membersRepository;
    }


}
