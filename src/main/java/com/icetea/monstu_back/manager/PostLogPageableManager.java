package com.icetea.monstu_back.manager;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.model.PostLog;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

@Component
public class PostLogPageableManager implements PageableManager<PostLog>{

    public PostLogPageableManager() {}

    @Override
    public CustomPageableDTO extract(ServerRequest request) {
        CustomPageableDTO dto = extractDefault(request);
        if (dto.getPage() < 0 || dto.getSize() <= 0) throw new IllegalArgumentException("유효한 Page, Size 값이여야 합니다.");
        return dto;
    }

    @Override
    public Sort createSort(CustomPageableDTO dto) {
        return createSortDefault(dto);
    }

    @Override
    public Class<?> convertFilterValue(String filterOption) {
        return switch (filterOption) {
            case "postId", "authorId","id","viewCount" -> Long.class;
            case "createdAt","lastView" -> LocalDateTime.class;
            default -> String.class;
        };
    }
}
