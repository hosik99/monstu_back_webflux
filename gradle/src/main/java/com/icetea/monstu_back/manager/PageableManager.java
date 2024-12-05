package com.icetea.monstu_back.manager;

import com.icetea.monstu_back.dto.CustomPageableDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PageableManager<T> {
    CustomPageableDTO extract(ServerRequest request);   // request에서 값을 추출해서 CustomPageableDTO를 반환
    Sort createSort(CustomPageableDTO dto);     // CustomPageableDTO 객체를 사용해서 Sort 객체를 반환
    Class<?> convertFilterValue(String filterOption);     //filterOption 타입을 확인

    default CustomPageableDTO extractDefault(ServerRequest request) {
        String dateStartParam = request.queryParam("dateStart").orElse(null);
        String dateEndParam = request.queryParam("dateEnd").orElse(null);

        return CustomPageableDTO.builder()
                .page(Integer.parseInt(request.pathVariable("page")))
                .size(Integer.parseInt(request.pathVariable("size")))

                .sortValue(request.queryParam("sortValue").orElse("id"))
                .sortDirection(request.queryParam("sortDirection").orElse("DESC"))

                .filterOption(request.queryParam("filterOption").orElse(null))
                .filterValue(request.queryParam("filterValue").orElse(null))

                .dateOption(request.queryParam("dateOption").orElse(null))
                .dateStart( (dateStartParam != null) ? LocalDate.parse(dateStartParam).atStartOfDay() : null )
                .dateEnd( (dateEndParam != null) ? LocalDate.parse(dateEndParam).atTime(23, 59, 59) : null )
                .build();
    }


    default Sort createSortDefault(CustomPageableDTO dto) {
        return dto.getSortDirection().equals("DESC")
                ? Sort.by(Sort.Direction.DESC, dto.getSortValue())
                : Sort.by(Sort.Direction.ASC, dto.getSortValue());
    }

    default <U> U castValue(Class<U> clazz, String value) {
        if (clazz == Long.class) {
            return clazz.cast(Long.parseLong(value));
        } else if (clazz == LocalDateTime.class) {
            return clazz.cast(LocalDateTime.parse(value));
        } else if (clazz == String.class) {
            return clazz.cast(value);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + clazz.getName());
        }
    }

}


