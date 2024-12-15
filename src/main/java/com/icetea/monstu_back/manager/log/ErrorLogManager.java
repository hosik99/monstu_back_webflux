package com.icetea.monstu_back.manager.log;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import com.icetea.monstu_back.manager.PageableManager;
import com.icetea.monstu_back.model.log.ErrorLog;
import com.icetea.monstu_back.model.log.PostLog;
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
