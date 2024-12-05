package com.icetea.monstu_back.repository.custom;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.manager.PostLogPageableManager;
import com.icetea.monstu_back.model.PostLog;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public class CustomPostLogRepositoryImpl implements CustomPostLogRepository {
    private final ReactiveMongoTemplate mongoTemplate;
    private final PostLogPageableManager pageableManager;

    public CustomPostLogRepositoryImpl(ReactiveMongoTemplate mongoTemplate,PostLogPageableManager pageableManager) {
        this.mongoTemplate = mongoTemplate;
        this.pageableManager = pageableManager;
    }

    // 정렬검색
    @Override
    public Flux<PostLog> findWithPagination( CustomPageableDTO dto ) {
        Query query = new Query().with(PageRequest.of( dto.getPage() ,dto.getSize() , pageableManager.createSort(dto)));
        return mongoTemplate.find(query, PostLog.class);
    }

    //정렬, 필터링 검색
    @Override
    public Flux<PostLog> findByWithPagination( CustomPageableDTO dto ) {
        Class<?> type = pageableManager.convertFilterValue( dto.getFilterOption() );

        Query query = new Query()
                .addCriteria(Criteria.where( dto.getFilterOption() ).is( pageableManager.castValue(type,dto.getFilterValue()) ))
                .with(PageRequest.of( dto.getPage() , dto.getSize() , pageableManager.createSort(dto)));
        return mongoTemplate.find(query, PostLog.class).log();
    }

    //정렬, Date 필터링
    @Override
    public Flux<PostLog> findByDateWithPagination( CustomPageableDTO dto ) {
        Query query = new Query()
                .addCriteria( Criteria.where( dto.getDateOption() ).gte( dto.getDateStart() ).lte( dto.getDateEnd() ) )
                .with(PageRequest.of(dto.getPage(), dto.getSize(), pageableManager.createSort(dto)));
        return mongoTemplate.find(query, PostLog.class).log();
    }

    // 정렬, 필터링, Date 필터링
    @Override
    public Flux<PostLog> findWithOption( CustomPageableDTO dto ) {
        Class<?> type = pageableManager.convertFilterValue( dto.getFilterOption() );

        Query query = new Query()
                .addCriteria(Criteria.where(dto.getFilterOption()).is(pageableManager.castValue(type, dto.getFilterValue()))
                        .and(dto.getDateOption()).gte( dto.getDateStart() ).lte( dto.getDateEnd() ))
                .with(PageRequest.of(dto.getPage(), dto.getSize(), pageableManager.createSort(dto)));
        return mongoTemplate.find(query, PostLog.class).log();
    }




}

