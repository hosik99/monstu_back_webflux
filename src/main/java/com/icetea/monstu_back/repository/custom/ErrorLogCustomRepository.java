package com.icetea.monstu_back.repository.custom;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.manager.log.ErrorLogManager;
import com.icetea.monstu_back.model.log.ErrorLog;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class ErrorLogCustomRepository implements PageableCustomRepository<ErrorLog>{

    private final ReactiveMongoTemplate mongoTemplate;
    private final ErrorLogManager errorLogManager;

    public ErrorLogCustomRepository(ReactiveMongoTemplate mongoTemplate, ErrorLogManager errorLogManager) {
        this.mongoTemplate = mongoTemplate;
        this.errorLogManager = errorLogManager;
    }

    // 정렬검색
    @Override
    public Flux<ErrorLog> findWithPagination(CustomPageableDTO dto ) {
        Query query = new Query().with(PageRequest.of( dto.getPage() ,dto.getSize() , errorLogManager.createSort(dto)));
        return mongoTemplate.find(query, ErrorLog.class);
    }

    //정렬, 필터링 검색
    @Override
    public Flux<ErrorLog> findByWithPagination( CustomPageableDTO dto ) {
        Class<?> type = errorLogManager.convertFilterValue( dto.getFilterOption() );

        Query query = new Query()
                .addCriteria(Criteria.where( dto.getFilterOption() ).is( errorLogManager.castValue(type,dto.getFilterValue()) ))
                .with(PageRequest.of( dto.getPage() , dto.getSize() , errorLogManager.createSort(dto)));
        return mongoTemplate.find(query, ErrorLog.class).log();
    }

    //정렬, Date 필터링
    @Override
    public Flux<ErrorLog> findByDateWithPagination( CustomPageableDTO dto ) {
        Query query = new Query()
                .addCriteria( Criteria.where( dto.getDateOption() ).gte( dto.getDateStart() ).lte( dto.getDateEnd() ) )
                .with(PageRequest.of(dto.getPage(), dto.getSize(), errorLogManager.createSort(dto)));
        return mongoTemplate.find(query, ErrorLog.class).log();
    }

    // 정렬, 필터링, Date 필터링
    @Override
    public Flux<ErrorLog> fincWithOptions( CustomPageableDTO dto ) {
        Class<?> type = errorLogManager.convertFilterValue( dto.getFilterOption() );

        Query query = new Query()
                .addCriteria(Criteria.where(dto.getFilterOption()).is(errorLogManager.castValue(type, dto.getFilterValue()))
                        .and(dto.getDateOption()).gte( dto.getDateStart() ).lte( dto.getDateEnd() ))
                .with(PageRequest.of(dto.getPage(), dto.getSize(), errorLogManager.createSort(dto)));
        return mongoTemplate.find(query, ErrorLog.class).log();
    }
}
