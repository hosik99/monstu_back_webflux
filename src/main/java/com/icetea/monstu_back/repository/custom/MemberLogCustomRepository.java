package com.icetea.monstu_back.repository.custom;

import com.icetea.monstu_back.mongo.pageable.CustomPageableDTO;
import com.icetea.monstu_back.manager.log.MemberLogManager;
import com.icetea.monstu_back.model.log.MemberLog;
import com.icetea.monstu_back.mongo.pageable.PageableCustomRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class MemberLogCustomRepository implements PageableCustomRepository<MemberLog> {

    private final ReactiveMongoTemplate mongoTemplate;
    private final MemberLogManager memberLogManager;

    public MemberLogCustomRepository(ReactiveMongoTemplate mongoTemplate, MemberLogManager memberLogManager) {
        this.mongoTemplate = mongoTemplate;
        this.memberLogManager = memberLogManager;
    }

    // 정렬검색
    @Override
    public Flux<MemberLog> findWithPagination(CustomPageableDTO dto ) {
        Query query = new Query().with(PageRequest.of( dto.getPage() ,dto.getSize() , memberLogManager.createSort(dto)));
        return mongoTemplate.find(query, MemberLog.class);
    }

    //정렬, 필터링 검색
    @Override
    public Flux<MemberLog> findByWithPagination( CustomPageableDTO dto ) {
        Class<?> type = memberLogManager.convertFilterValue( dto.getFilterOption() );

        Query query = new Query()
                .addCriteria(Criteria.where( dto.getFilterOption() ).is( memberLogManager.castValue(type,dto.getFilterValue()) ))
                .with(PageRequest.of( dto.getPage() , dto.getSize() , memberLogManager.createSort(dto)));
        return mongoTemplate.find(query, MemberLog.class).log();
    }

    //정렬, Date 필터링
    @Override
    public Flux<MemberLog> findByDateWithPagination( CustomPageableDTO dto ) {
        Query query = new Query()
                .addCriteria( Criteria.where( dto.getDateOption() ).gte( dto.getDateStart() ).lte( dto.getDateEnd() ) )
                .with(PageRequest.of(dto.getPage(), dto.getSize(), memberLogManager.createSort(dto)));
        return mongoTemplate.find(query, MemberLog.class).log();
    }

    // 정렬, 필터링, Date 필터링
    @Override
    public Flux<MemberLog> findWithOptions( CustomPageableDTO dto ) {
        Class<?> type = memberLogManager.convertFilterValue( dto.getFilterOption() );

        Query query = new Query()
                .addCriteria(Criteria.where(dto.getFilterOption()).is(memberLogManager.castValue(type, dto.getFilterValue()))
                        .and(dto.getDateOption()).gte( dto.getDateStart() ).lte( dto.getDateEnd() ))
                .with(PageRequest.of(dto.getPage(), dto.getSize(), memberLogManager.createSort(dto)));
        return mongoTemplate.find(query, MemberLog.class).log();
    }

}
