package com.icetea.monstu_back.handler.log;

import com.icetea.monstu_back.mongo.pageable.CustomPageableDTO;
import com.icetea.monstu_back.manager.log.CategoryLogManager;
import com.icetea.monstu_back.model.log.CategoryLog;
import com.icetea.monstu_back.model.log.PostLog;
import com.icetea.monstu_back.repository.custom.CategoryLogCustomRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CategoryLogHandler {

    private final CategoryLogCustomRepository cateLogCustomRps;
    private final CategoryLogManager cateLogManager;

    public CategoryLogHandler(CategoryLogCustomRepository cateLogCustomRps, CategoryLogManager cateLogManager ) {
        this.cateLogCustomRps = cateLogCustomRps;
        this.cateLogManager = cateLogManager;
    }

    public Mono<ServerResponse> getCategoryLog(ServerRequest request) {
        CustomPageableDTO pageableDTO = cateLogManager.extract(request);   // page,size 추출

        Flux<CategoryLog> categoryLogFlux = getCategoryLogs( pageableDTO ) ;

        return categoryLogFlux.hasElements()
                .flatMap(hasElements -> hasElements
                        ? ServerResponse.ok().body(categoryLogFlux, PostLog.class)
                        : ServerResponse.noContent().build());  // return HTTP Status 204
    }

    // Get PostLog
    private Flux<CategoryLog> getCategoryLogs( CustomPageableDTO dto ) {
        Boolean filterBoo = dto.getFilterOption() != null && dto.getFilterValue() != null;
        Boolean dateFilterBoo = dto.getDateOption() != null && dto.getDateStart() != null && dto.getDateEnd() != null;

        System.out.println(dto);
        System.out.println("filterBoo: " + filterBoo);
        System.out.println("dateFilterBoo: " + dateFilterBoo);

        if( filterBoo && dateFilterBoo ){
            return cateLogCustomRps.findWithOptions(dto);  // filtering, Date Filtering
        }else if( !filterBoo && !dateFilterBoo ){
            return cateLogCustomRps.findWithPagination(dto);  //just find
        } else if ( !filterBoo) {
            return cateLogCustomRps.findByDateWithPagination(dto);    //Date Filtering
        }else {
            return cateLogCustomRps.findByWithPagination(dto);    // filtering
        }
    }

}

