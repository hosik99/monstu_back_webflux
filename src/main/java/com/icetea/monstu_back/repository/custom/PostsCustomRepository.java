package com.icetea.monstu_back.repository.custom;

import com.icetea.monstu_back.dto.PrePostsDTO;
import com.icetea.monstu_back.enums.State;
import com.icetea.monstu_back.manager.PostsManager;
import com.icetea.monstu_back.r2dbc.pageable.CustomPageableDTO;
import com.icetea.monstu_back.r2dbc.pageable.PageableCustomRepository;
import com.icetea.monstu_back.r2dbc.sqlBuilder.SqlBuilder;
import com.kclass.generated.KMembers;
import com.kclass.generated.KPostCategory;
import com.kclass.generated.KPosts;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


import java.time.LocalDateTime;


@Repository
public class PostsCustomRepository implements PageableCustomRepository<PrePostsDTO> {

    private final PostsManager postsMng;
    private final DatabaseClient databaseClient;
    private final SqlBuilder sqlBuilder;

    public PostsCustomRepository(PostsManager postsMng, DatabaseClient databaseClient, SqlBuilder sqlBuilder) {
        this.postsMng = postsMng;
        this.databaseClient = databaseClient;
        this.sqlBuilder = sqlBuilder;
    }


    // 정렬검색
    @Override
    public Flux<PrePostsDTO> findWithPagination(CustomPageableDTO pageableDTO) {
        KPosts kPosts = new KPosts().withNick("p");
        KMembers kMembers = new KMembers().withNick("m");
        KPostCategory kPostCategory = new KPostCategory().withNick("c");

        String sql  = sqlBuilder
                .select( "p.id", "m.name", "p.title", "p.thumbnail_url","p.created_at","p.updated_at","c.category", "p.state", "p.is_public" )
                .from("posts").as("p")
                .join("post_category").as("c")
                .on("p.category_id").eq("c.id")
                .join("members").as("m")
                .on("p.author_id").eq("m.id")
                .orderBy( postsMng.createSortDefault(pageableDTO),"p")
                .limit(pageableDTO.getSize())
                .offset(pageableDTO.getPage()*pageableDTO.getSize())
                .build();

        return databaseClient.sql(sql)
                .map(row -> PrePostsDTO.builder()
                        .id(row.get("p_id", Long.class))
                        .author(row.get("m_name", String.class))
                        .title(row.get("p_title", String.class))
                        .thumbnailUrl(row.get("p_thumbnail_url", String.class))
                        .createdAt(row.get("p_created_at", LocalDateTime.class))
                        .updatedAt(row.get("p_updated_at", LocalDateTime.class))
                        .category(row.get("c_category", String.class))
                        .state(State.valueOf(row.get("p_state", String.class)))
                        .isPublic(row.get("p_is_public", Boolean.class))
                        .build() )
                .all(); // 모든 결과를 비동기적으로 가져오며 return Flux
    }

    // filter를 추가한 Pageable 검색
    @Override
    public Flux<PrePostsDTO> findByWithPagination(CustomPageableDTO pageableDTO) {
        String sql  = sqlBuilder
                .select( "p.id", "m.name", "p.title", "p.thumbnail_url","p.created_at","p.updated_at","c.category", "p.state", "p.is_public" )
                .from("posts").as("p")
                .join("post_category").as("c")
                .on("p.category_id").eq("c.id")
                .join("members").as("m")
                .on("p.author_id").eq("m.id")
                .where(pageableDTO.getFilterOption()).eq(pageableDTO.getFilterValue())
                .orderBy( postsMng.createSortDefault(pageableDTO),"p")
                .limit(pageableDTO.getSize())
                .offset(pageableDTO.getPage()*pageableDTO.getSize())
                .build();

        return databaseClient.sql(sql)
                .map(row -> PrePostsDTO.builder()
                        .id(row.get("p_id", Long.class))
                        .author(row.get("m_name", String.class))
                        .title(row.get("p_title", String.class))
                        .thumbnailUrl(row.get("p_thumbnail_url", String.class))
                        .createdAt(row.get("p_created_at", LocalDateTime.class))
                        .updatedAt(row.get("p_updated_at", LocalDateTime.class))
                        .category(row.get("c_category", String.class))
                        .state(State.valueOf(row.get("p_state", String.class)))
                        .isPublic(row.get("p_is_public", Boolean.class))
                        .build() )
                .all(); // 모든 결과를 비동기적으로 가져오며 return Flux
    }

    // Date Filtering
    @Override
    public Flux<PrePostsDTO> findByDateWithPagination(CustomPageableDTO dto) {
        return null;
    }

    // filtering & Date Filtering
    @Override
    public Flux<PrePostsDTO> findWithOptions(CustomPageableDTO dto) {
        return null;
    }
}
