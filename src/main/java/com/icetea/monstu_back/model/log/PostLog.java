package com.icetea.monstu_back.model.log;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "post_log")
public class PostLog {

    @Id
    private String id;

    private String title;

    @Field("post_id")
    private Long postId;

    @Field("author_id")
    private Long authorId;

    @Field("view_count")
    private Long viewCount;

    @Field("last_view")
    private LocalDateTime lastView;

    @Builder.Default
    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
