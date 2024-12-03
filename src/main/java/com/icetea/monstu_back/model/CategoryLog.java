package com.icetea.monstu_back.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "post_category_log")
public class CategoryLog {

    @Id
    private String id;

    @Field("view_count")
    private int viewCount;

    @Field("post_category")
    private String postCategory;

    @Builder.Default
    @Column("date")
    private LocalDate date = LocalDate.now();

}
