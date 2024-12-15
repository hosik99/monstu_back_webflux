package com.icetea.monstu_back.handler.log;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.manager.log.PostLogManager;
import com.icetea.monstu_back.model.log.PostLog;
import com.icetea.monstu_back.repository.PostsRepository;
import com.icetea.monstu_back.repository.custom.PostLogCustomRepository;
import com.icetea.monstu_back.repository.log.PostLogRepository;
import org.bson.types.ObjectId;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PostsLogHandler {

    private final PostLogCustomRepository postLogCustomRps;
    private final PostLogRepository postLogRps;
    private final PostsRepository postsRps;
    private final PostLogManager pageableManager;

    public PostsLogHandler(PostLogCustomRepository postLogCustomRps, PostLogManager pageableManager,
                        PostsRepository postsRps, PostLogRepository postLogRps) {
        this.postLogCustomRps = postLogCustomRps;
        this.postLogRps = postLogRps;
        this.postsRps = postsRps;
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

    @Transactional
    public Mono<ServerResponse> deleteAllByIdPostLog(ServerRequest request) {
        /* 요청 본문을 List<String> 형식으로 변환, 배열 또는 리스트와 같은 컬렉션 타입의 데이터를 받을 때 사용*/
        return request.bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                .flatMap(postLogRps::deleteAllById)
                .then(ServerResponse.ok().build())
                .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue("Error occurred while deleting posts: " + e.getMessage()));
    }

    public Mono<ServerResponse> deleteByIdPostLog(ServerRequest request) {
        String postId = request.pathVariable("id");
        return postLogRps.findById(postId)
                .flatMap(postLogRps::delete)
                .then(ServerResponse.ok().build())
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue("Error occurred while deleting post: " + e.getMessage()));
    }

}
