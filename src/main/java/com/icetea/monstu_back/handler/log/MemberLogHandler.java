package com.icetea.monstu_back.handler.log;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.manager.log.MemberLogManager;
import com.icetea.monstu_back.model.log.ErrorLog;
import com.icetea.monstu_back.model.log.MemberLog;
import com.icetea.monstu_back.model.log.PostLog;
import com.icetea.monstu_back.repository.custom.MemberLogCustomRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MemberLogHandler {

    private final MemberLogCustomRepository memberLogCustomRps;
    private final MemberLogManager memberLogManager;

    public MemberLogHandler( MemberLogCustomRepository memberLogCustomRps, MemberLogManager memberLogManager ) {
        this.memberLogCustomRps = memberLogCustomRps;
        this.memberLogManager = memberLogManager;
    }

    public Mono<ServerResponse> getMemberLog(ServerRequest request) {
        CustomPageableDTO pageableDTO = memberLogManager.extract(request);   // page,size 추출

        Flux<MemberLog> memberLogFlux = getMemberLogs( pageableDTO ) ;

        return memberLogFlux.hasElements()
                .flatMap(hasElements -> hasElements
                        ? ServerResponse.ok().body(memberLogFlux, MemberLog.class)
                        : ServerResponse.noContent().build());  // return HTTP Status 204
    }

    // Get PostLog
    private Flux<MemberLog> getMemberLogs( CustomPageableDTO dto ) {
        Boolean filterBoo = dto.getFilterOption() != null && dto.getFilterValue() != null;
        Boolean dateFilterBoo = dto.getDateOption() != null && dto.getDateStart() != null && dto.getDateEnd() != null;

        System.out.println(dto);
        System.out.println("filterBoo: " + filterBoo);
        System.out.println("dateFilterBoo: " + dateFilterBoo);

        if( filterBoo && dateFilterBoo ){
            return memberLogCustomRps.fincWithOptions(dto);  // filtering, Date Filtering
        }else if( !filterBoo && !dateFilterBoo ){
            return memberLogCustomRps.findWithPagination(dto);  //just find
        } else if ( !filterBoo) {
            return memberLogCustomRps.findByDateWithPagination(dto);    //Date Filtering
        }else {
            return memberLogCustomRps.findByWithPagination(dto);    // filtering
        }
    }

}
