package com.icetea.monstu_back.handler;

import com.icetea.monstu_back.dto.PostsDTO;
import com.icetea.monstu_back.dto.PrePostsDTO;
import com.icetea.monstu_back.manager.PostsManager;
import com.icetea.monstu_back.model.Posts;
import com.icetea.monstu_back.model.log.PostLog;
import com.icetea.monstu_back.r2dbc.pageable.CustomPageableDTO;
import com.icetea.monstu_back.repository.PostsRepository;
import com.icetea.monstu_back.repository.custom.PostsCustomRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PostsHandler {

    private final PostsCustomRepository postsCustomRps;
    private final PostsRepository postsRps;
    private final PostsManager postsMng;

    public PostsHandler(PostsCustomRepository postsCustomRps,PostsRepository postsRps,PostsManager postsMng) {
        this.postsCustomRps = postsCustomRps;
        this.postsRps = postsRps;
        this.postsMng = postsMng;
    }

    public Mono<ServerResponse> getPostByPageable(ServerRequest request) {
        CustomPageableDTO pageableDTO = postsMng.extract(request);   // page,size... 추출
        System.out.println(pageableDTO.toString());
        Flux<PrePostsDTO> postFlux = getPosts( pageableDTO ) ;

        return postFlux.hasElements()
                .flatMap(hasElements -> hasElements
                        ? ServerResponse.ok().body(postFlux, PostsDTO.class)
                        : ServerResponse.noContent().build());  // return HTTP Status 204
    }

    // Get Posts With Filtering
    private Flux<PrePostsDTO> getPosts(CustomPageableDTO dto) {
        Boolean filterBoo = dto.getFilterOption() != null && dto.getFilterValue() != null;
        Boolean dateFilterBoo = dto.getDateOption() != null && dto.getDateStart() != null && dto.getDateEnd() != null;

        if( filterBoo && dateFilterBoo ){
            return postsCustomRps.findWithOptions(dto);  // filtering, Date Filtering
        }else if( !filterBoo && !dateFilterBoo ){
            return postsCustomRps.findWithPagination(dto);  //just find
        } else if ( !filterBoo) {
            return postsCustomRps.findByDateWithPagination(dto);    //Date Filtering
        }else {
            return postsCustomRps.findByWithPagination(dto);    // filtering
        }
    }


}
