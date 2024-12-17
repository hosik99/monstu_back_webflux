package com.icetea.monstu_back.manager;

import com.icetea.monstu_back.model.Posts;
import com.icetea.monstu_back.r2dbc.pageable.CustomPageableDTO;
import com.icetea.monstu_back.r2dbc.pageable.PageableManager;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

@Component
public class PostsManager implements PageableManager<Posts> {

    public PostsManager() {}


    @Override
    public CustomPageableDTO extract(ServerRequest request) { return extractDefault(request); }

    @Override
    public Sort createSort(CustomPageableDTO dto) { return createSortDefault(dto); }

    @Override
    public Class<?> convertFilterValue(String filterOption) {
        return switch (filterOption) {
            case "id", "authorId"-> Long.class;
            case "category" -> Integer.class;
            case "createdAt","updatedAt" -> LocalDateTime.class;
            default -> String.class;
        };
    }
}
