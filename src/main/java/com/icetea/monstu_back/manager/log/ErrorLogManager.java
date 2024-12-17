package com.icetea.monstu_back.manager.log;

import com.icetea.monstu_back.mongo.pageable.CustomPageableDTO;
import com.icetea.monstu_back.mongo.pageable.PageableManager;
import com.icetea.monstu_back.model.log.ErrorLog;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

@Component
public class ErrorLogManager implements PageableManager<ErrorLog> {

    public ErrorLogManager() {}

    @Override
    public CustomPageableDTO extract(ServerRequest request) {
        return extractDefault(request);
    }

    @Override
    public Sort createSort(CustomPageableDTO dto) {
        return createSortDefault(dto);
    }

    @Override
    public Class<?> convertFilterValue(String filterOption) {
        return switch (filterOption) {
            case "userId" -> Long.class;
            case "code" -> Integer.class;
            case "timestamp" -> LocalDateTime.class;
            default -> String.class;
        };
    }
}
