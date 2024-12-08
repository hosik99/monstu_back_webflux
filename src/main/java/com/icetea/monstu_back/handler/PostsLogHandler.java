package com.icetea.monstu_back.handler;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.manager.log.PostLogPageableManager;
import com.icetea.monstu_back.model.log.PostLog;
import com.icetea.monstu_back.repository.custom.PostLogCustomRepository;
import com.icetea.monstu_back.repository.log.PostLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PostsLogHandler {

    private final PostLogCustomRepository postLogCustomRps;
    private final PostLogPageableManager pageableManager;

    public PostsLogHandler(PostLogCustomRepository postLogCustomRps, PostLogPageableManager pageableManager) {
        this.postLogCustomRps = postLogCustomRps;
        this.pageableManager = pageableManager;
    }

    public Mono<ServerResponse> getPostLog(ServerRequest request) {
        CustomPageableDTO pageableDTO = pageableManager.extract(request);   // page,size 추출

        Flux<PostLog> postLogsFlux = getPostLogs( pageableDTO ) ;

        return postLogsFlux.hasElements()
                .flatMap(hasElements -> hasElements
                        ? ServerResponse.ok().body(postLogsFlux, PostLog.class)
                        : ServerResponse.noContent().build());  // return HTTP Status 204
    }

    // Get PostLog
    private Flux<PostLog> getPostLogs( CustomPageableDTO dto ) {
        Boolean filterBoo = dto.getFilterOption() != null && dto.getFilterValue() != null;
        Boolean dateFilterBoo = dto.getDateOption() != null && dto.getDateStart() != null && dto.getDateEnd() != null;

        System.out.println(dto);
        System.out.println("filterBoo: " + filterBoo);
        System.out.println("dateFilterBoo: " + dateFilterBoo);

        if( filterBoo && dateFilterBoo ){
            return postLogCustomRps.fincWithOptions(dto);  // filtering, Date Filtering
        }else if( !filterBoo && !dateFilterBoo ){
            return postLogCustomRps.findWithPagination(dto);  //just find
        } else if ( !filterBoo) {
            return postLogCustomRps.findByDateWithPagination(dto);    //Date Filtering
        }else {
            return postLogCustomRps.findByWithPagination(dto);    // filtering
        }
    }

}
