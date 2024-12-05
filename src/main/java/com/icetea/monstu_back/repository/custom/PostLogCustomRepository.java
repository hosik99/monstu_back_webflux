package com.icetea.monstu_back.repository.custom;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.manager.log.PostLogPageableManager;
import com.icetea.monstu_back.model.log.PostLog;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class PostLogCustomRepository implements PageableCustomRepository<PostLog> {
    private final ReactiveMongoTemplate mongoTemplate;
    private final PostLogPageableManager pageableManager;

    public PostLogCustomRepository(ReactiveMongoTemplate mongoTemplate, PostLogPageableManager pageableManager) {
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
    public Flux<PostLog> fincWithOptions( CustomPageableDTO dto ) {
        Class<?> type = pageableManager.convertFilterValue( dto.getFilterOption() );

        Query query = new Query()
                .addCriteria(Criteria.where(dto.getFilterOption()).is(pageableManager.castValue(type, dto.getFilterValue()))
                        .and(dto.getDateOption()).gte( dto.getDateStart() ).lte( dto.getDateEnd() ))
                .with(PageRequest.of(dto.getPage(), dto.getSize(), pageableManager.createSort(dto)));
        return mongoTemplate.find(query, PostLog.class).log();
    }
}
