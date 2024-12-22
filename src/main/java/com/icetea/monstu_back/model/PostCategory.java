package com.icetea.monstu_back.model;

import com.icetea.monstu_back.r2dbc.sqlBuilder.GenerateKClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@GenerateKClass
@Table("post_category")
public class PostCategory {

    @Id
    private Long id;

    private String category;
}
