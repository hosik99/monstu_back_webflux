package com.icetea.monstu_back.repository.custom;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import reactor.core.publisher.Flux;

public interface PageableCustomRepository<T> {

    Flux<T> findWithPagination( CustomPageableDTO pageableDTO );

    // filter를 추가한 Pageable 검색
    Flux<T> findByWithPagination( CustomPageableDTO pageableDTO );

    // Date Filtering
    Flux<T> findByDateWithPagination( CustomPageableDTO dto );

    // filtering & Date Filtering
    Flux<T> fincWithOptions( CustomPageableDTO dto );
}
