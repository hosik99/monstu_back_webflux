package com.icetea.monstu_back.handler;

import com.icetea.monstu_back.model.Members;
import com.icetea.monstu_back.repository.MembersRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MembersHandler {

    private final MembersRepository membersRps;

    public MembersHandler(MembersRepository membersRps) {
        this.membersRps = membersRps;
    }

    public Mono<ServerResponse> getAllMembers(ServerRequest request) {
        Flux<Members> membersFlux = membersRps.findAll();
        return ServerResponse.ok().body( membersFlux, Members.class );
    }

}
