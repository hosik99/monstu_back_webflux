package com.icetea.monstu_back.repository.custom;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.model.PostLog;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;

public interface CustomPostLogRepository {

    Flux<PostLog> findWithPagination( CustomPageableDTO pageableDTO );

    // filter를 추가한 Pageable 검색
    Flux<PostLog> findByWithPagination( CustomPageableDTO pageableDTO );
}
