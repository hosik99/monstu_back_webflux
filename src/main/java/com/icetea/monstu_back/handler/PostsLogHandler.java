package com.icetea.monstu_back.handler;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.manager.PostLogPageableManager;
import com.icetea.monstu_back.model.PostLog;
import com.icetea.monstu_back.repository.PostLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PostsLogHandler {

    private final PostLogRepository postLogRps;
    private final PostLogPageableManager pageableManager;

    public PostsLogHandler(PostLogRepository postLogRepository,PostLogPageableManager pageableManager) {
        this.postLogRps = postLogRepository;
        this.pageableManager = pageableManager;
    }

    // Get Top Posts View Count
    public Mono<ServerResponse> getPostLog(ServerRequest request) {
        CustomPageableDTO pageableDTO = pageableManager.extract(request);   // page,size 추출

        Flux<PostLog> postLogsFlux = getPostLogs( pageableDTO ) ;

        return postLogsFlux.hasElements()
                .flatMap(hasElements -> hasElements
                        ? ServerResponse.ok().body(postLogsFlux, PostLog.class)
                        : ServerResponse.noContent().build());  // return HTTP Status 204
    }

    private Flux<PostLog> getPostLogs( CustomPageableDTO pageableDTO ) {
        return pageableDTO.getFilterOption() != null && pageableDTO.getFilterValue() != null
                ? postLogRps.findByWithPagination( pageableDTO )
                : postLogRps.findWithPagination( pageableDTO );
    }

}
