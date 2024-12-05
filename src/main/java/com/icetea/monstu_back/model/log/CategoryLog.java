package com.icetea.monstu_back.model.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "post_category_log")
public class CategoryLog {

    @Id
    private String id;

    @Field("view_count")
    private Long viewCount;

    @Field("category")
    private String category;

    @Builder.Default
    @Field("date")
    private LocalDateTime date = LocalDateTime.now();

}
