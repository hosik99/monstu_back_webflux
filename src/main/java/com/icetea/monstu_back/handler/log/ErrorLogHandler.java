package com.icetea.monstu_back.handler.log;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.manager.log.ErrorLogManager;
import com.icetea.monstu_back.model.log.ErrorLog;
import com.icetea.monstu_back.repository.custom.ErrorLogCustomRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ErrorLogHandler {

    private final ErrorLogCustomRepository errorLogCustomRps;
    private final ErrorLogManager errorLogManager;

    public ErrorLogHandler( ErrorLogCustomRepository errorLogCustomRps, ErrorLogManager errorLogManager ) {
        this.errorLogCustomRps = errorLogCustomRps;
        this.errorLogManager = errorLogManager;
    }

    public Mono<ServerResponse> getErrorLog(ServerRequest request) {
        CustomPageableDTO pageableDTO = errorLogManager.extract(request);   // page,size 추출

        Flux<ErrorLog> errorLogFlux = getErrorLogs( pageableDTO ) ;

        return errorLogFlux.hasElements()
                .flatMap(hasElements -> hasElements
                        ? ServerResponse.ok().body(errorLogFlux, ErrorLog.class)
                        : ServerResponse.noContent().build());  // return HTTP Status 204
    }

    // Get PostLog
    private Flux<ErrorLog> getErrorLogs( CustomPageableDTO dto ) {
        Boolean filterBoo = dto.getFilterOption() != null && dto.getFilterValue() != null;
        Boolean dateFilterBoo = dto.getDateOption() != null && dto.getDateStart() != null && dto.getDateEnd() != null;

        System.out.println(dto);
        System.out.println("filterBoo: " + filterBoo);
        System.out.println("dateFilterBoo: " + dateFilterBoo);

        if( filterBoo && dateFilterBoo ){
            return errorLogCustomRps.fincWithOptions(dto);  // filtering, Date Filtering
        }else if( !filterBoo && !dateFilterBoo ){
            return errorLogCustomRps.findWithPagination(dto);  //just find
        } else if ( !filterBoo) {
            return errorLogCustomRps.findByDateWithPagination(dto);    //Date Filtering
        }else {
            return errorLogCustomRps.findByWithPagination(dto);    // filtering
        }
    }
}
