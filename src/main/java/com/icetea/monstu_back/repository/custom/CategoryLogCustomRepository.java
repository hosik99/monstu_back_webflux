package com.icetea.monstu_back.repository.custom;

import com.icetea.monstu_back.mongo.pageable.CustomPageableDTO;
import com.icetea.monstu_back.manager.log.CategoryLogManager;
import com.icetea.monstu_back.model.log.CategoryLog;
import com.icetea.monstu_back.mongo.pageable.PageableCustomRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CategoryLogCustomRepository implements PageableCustomRepository<CategoryLog> {
    private final ReactiveMongoTemplate mongoTemplate;
    private final CategoryLogManager cateLogManager;

    public CategoryLogCustomRepository(ReactiveMongoTemplate mongoTemplate, CategoryLogManager cateLogManager) {
        this.mongoTemplate = mongoTemplate;
        this.cateLogManager = cateLogManager;
    }

    // 정렬검색
    @Override
    public Flux<CategoryLog> findWithPagination(CustomPageableDTO dto ) {
        Query query = new Query().with(PageRequest.of( dto.getPage() ,dto.getSize() , cateLogManager.createSort(dto)));
        return mongoTemplate.find(query, CategoryLog.class);
    }

    //정렬, 필터링 검색
    @Override
    public Flux<CategoryLog> findByWithPagination( CustomPageableDTO dto ) {
        Class<?> type = cateLogManager.convertFilterValue( dto.getFilterOption() );

        Query query = new Query()
                .addCriteria(Criteria.where( dto.getFilterOption() ).is( cateLogManager.castValue(type,dto.getFilterValue()) ))
                .with(PageRequest.of( dto.getPage() , dto.getSize() , cateLogManager.createSort(dto)));
        return mongoTemplate.find(query, CategoryLog.class).log();
    }

    //정렬, Date 필터링
    @Override
    public Flux<CategoryLog> findByDateWithPagination( CustomPageableDTO dto ) {
        Query query = new Query()
                .addCriteria( Criteria.where( dto.getDateOption() ).gte( dto.getDateStart() ).lte( dto.getDateEnd() ) )
                .with(PageRequest.of(dto.getPage(), dto.getSize(), cateLogManager.createSort(dto)));
        return mongoTemplate.find(query, CategoryLog.class).log();
    }

    // 정렬, 필터링, Date 필터링
    @Override
    public Flux<CategoryLog> findWithOptions( CustomPageableDTO dto ) {
        Class<?> type = cateLogManager.convertFilterValue( dto.getFilterOption() );

        Query query = new Query()
                .addCriteria(Criteria.where(dto.getFilterOption()).is(cateLogManager.castValue(type, dto.getFilterValue()))
                        .and(dto.getDateOption()).gte( dto.getDateStart() ).lte( dto.getDateEnd() ))
                .with(PageRequest.of(dto.getPage(), dto.getSize(), cateLogManager.createSort(dto)));
        return mongoTemplate.find(query, CategoryLog.class).log();
    }
}
