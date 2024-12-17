package com.icetea.monstu_back.manager.log;

import com.icetea.monstu_back.mongo.pageable.CustomPageableDTO;
import com.icetea.monstu_back.mongo.pageable.PageableManager;
import com.icetea.monstu_back.model.log.MemberLog;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

@Component
public class MemberLogManager implements PageableManager<MemberLog> {

    public MemberLogManager() {}

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
            case "memberId" -> Long.class;
            case "failedLoginCount" -> Integer.class;
            case "failedLoginAttempts","lastLogin","createdAt" -> LocalDateTime.class;
            default -> String.class;
        };
    }
}
