package com.icetea.monstu_back.repository.custom;

import com.icetea.monstu_back.dto.PrePostsDTO;
import com.icetea.monstu_back.enums.State;
import com.icetea.monstu_back.manager.PostsManager;
import com.icetea.monstu_back.r2dbc.pageable.CustomPageableDTO;
import com.icetea.monstu_back.r2dbc.pageable.PageableCustomRepository;
import com.icetea.monstu_back.r2dbc.sqlBuilder.AnnotationReader;
import com.icetea.monstu_back.r2dbc.sqlBuilder.SqlBuilder;
import com.kclass.generated.KMembers;
import com.kclass.generated.KPostCategory;
import com.kclass.generated.KPosts;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


import java.time.LocalDateTime;

/*
* findWithPagination() -> 대소문자에 대해 너무 엄격
* */


@Repository
public class PostsCustomRepository implements PageableCustomRepository<PrePostsDTO> {

    private final PostsManager postsMng;
    private final DatabaseClient databaseClient;

    public PostsCustomRepository(PostsManager postsMng, DatabaseClient databaseClient) {
        this.postsMng = postsMng;
        this.databaseClient = databaseClient;
    }

    // 정렬검색
    @Override
    public Flux<PrePostsDTO> findWithPagination(CustomPageableDTO pageableDTO) {
        String dateOption = pageableDTO.getDateOption();
        String filterOption = pageableDTO.getFilterOption();
        String sortValue = pageableDTO.getSortValue();
        String sortDirection = pageableDTO.getSortDirection();

        boolean filterBool = false; //Sorting_FilterOption
        boolean dateBool = false; //Sorting_DateOption

        StringBuffer sql = new StringBuffer();
            sql.append("SELECT p.id, m.name, p.title, p.thumbnail_url, p.created_at, p.updated_at, c.category, p.state, p.is_public ")
                .append(" FROM posts AS p ")
                .append(" JOIN post_category AS c ")
                .append(" ON p.category_id = c.id ")
                .append(" JOIN members AS m ")
                .append(" ON p.author_id = m.id ");

            // Append DateOption
            if( dateOption != null && !dateOption.isEmpty() ) {
                String optionFromSnake = toSnakeCase(dateOption);
                sql.append(" WHERE ").append(" p.").append(optionFromSnake).append(" BETWEEN :dateStart AND :dateEnd");
                dateBool = true;
            }

            // AppendmFilter Option
            if( filterOption != null && !filterOption.isEmpty() ) {
                filterOption = switch (filterOption){
                    case "category" -> "c.category";
                    case "author" -> "m.name";
                    default -> "p." + filterOption;
                };
                String optionFromSnake = toSnakeCase(filterOption);

                sql.append( (dateBool || filterBool) ? " AND " : " WHERE ");
                sql.append(optionFromSnake).append(" = :filterValue");

                filterBool = true;
            }

            sql.append(" ORDER BY ").append(" p.").append(sortValue).append(" ").append( sortDirection );
            sql.append(" LIMIT :size");
            sql.append(" OFFSET :page");

        System.out.println(sql.toString());
        DatabaseClient.GenericExecuteSpec executeSpec = databaseClient.sql( sql.toString() )
                .bind("size", pageableDTO.getSize())
                .bind("page", pageableDTO.getPage());

        if (dateBool) {
            executeSpec = executeSpec
                    .bind("dateStart", pageableDTO.getDateStart())
                    .bind("dateEnd", pageableDTO.getDateEnd());
        }
        if (filterBool) {
            String filterValue = pageableDTO.getFilterValue();
            if(filterValue.equals("true") || filterValue.equals("false")){
                boolean filterValueBool =  Boolean.parseBoolean(filterValue);
                executeSpec = executeSpec.bind("filterValue", filterValueBool);
            }else{
                executeSpec = executeSpec.bind("filterValue", filterValue);
            }

        }

        return executeSpec.map(row -> PrePostsDTO.builder()
                        .id(row.get("id", Long.class))
                        .author(row.get("name", String.class))
                        .title(row.get("title", String.class))
                        .thumbnailUrl(row.get("thumbnail_url", String.class))
                        .createdAt(row.get("created_at", LocalDateTime.class))
                        .updatedAt(row.get("updated_at", LocalDateTime.class))
                        .category(row.get("category", String.class))
                        .state(State.valueOf(row.get("state", String.class)))
                        .isPublic(row.get("is_public", Boolean.class))
                        .build())
                    .all();
    }

//    public String prePostsSQL(CustomPageableDTO pageableDTO){
//        KPosts kPosts = new KPosts().withNick("p");
//        KMembers kMembers = new KMembers().withNick("m");
//        KPostCategory kPostCategory = new KPostCategory().withNick("c");
//
//        String filterOption = pageableDTO.getFilterOption();
//        String dateOption = pageableDTO.getDateOption();
//        String sortValue = pageableDTO.getSortValue();
//
//        sqlBuilder.select( kPosts.id, kMembers.name, kPosts.title, kPosts.thumbnailUrl,kPosts.createdAt,kPosts.updatedAt,kPostCategory.category, kPosts.state, kPosts.isPublic )
//                .from(kPosts.table)
//                .join(kPostCategory.table)
//                .on(kPosts.category).eq(kPostCategory.id)
//                .join(kMembers.table)
//                .on(kPosts.authorId).eq(kMembers.id);
//
//        if(filterOption != null && !filterOption.isEmpty()){
//            filterOption = switch (filterOption){
//                case "category" -> kPostCategory.category;
//                case "author" -> kMembers.name;
//                default -> kPosts.nick+ "." + toSnakeCase(filterOption);
//            };
//            sqlBuilder.where(filterOption).eq(pageableDTO.getFilterValue());
//        }
//
//        if(dateOption != null && !dateOption.isEmpty()){
//            dateOption = kPosts.nick+ "." + toSnakeCase(dateOption);
//            String dateStart = "'" + String.valueOf(pageableDTO.getDateStart()).replace("T", " ") + "'";
//            String dateEnd = "'" + String.valueOf(pageableDTO.getDateEnd()).replace("T", " ") + "'";
//            sqlBuilder.where(dateOption).between(dateStart,dateEnd);
//        }
//
//        sqlBuilder.orderBy( kPosts.nick+ "." + toSnakeCase(sortValue), pageableDTO.getSortDirection())
//                .limit(pageableDTO.getSize())
//                .offset(pageableDTO.getPage()*pageableDTO.getSize());
//
//        return sqlBuilder.build();
//    }

    public String toSnakeCase(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }
        return camelCase
                .replaceAll("([a-z])([A-Z])", "$1_$2") // 소문자와 대문자 경계에 "_" 추가
                .toLowerCase(); // 소문자로 변환
    }
}
