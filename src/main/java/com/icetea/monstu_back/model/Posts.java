package com.icetea.monstu_back.model;

import com.icetea.monstu_back.enums.State;
import com.icetea.monstu_back.r2dbc.sqlBuilder.GenerateKClass;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
@GenerateKClass
@Table("posts")
public class Posts {

    @Id
    private Long id;

    @Column("author_id")
    private Long authorId;  //memberId (FK)

    private String title;

    private String content;

    @Column("thumbnail_url")
    private String thumbnailUrl;

    @Builder.Default
    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("category_id")
    private Long category;

    private State state;

    @Column("is_public")
    private Boolean isPublic;

}
