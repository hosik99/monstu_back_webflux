package com.icetea.monstu_back.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "sentences_eng_ko")
public class SentencesEngKo {

    @Id
    private String id;

    @Field("post_id")
    private Long postId;

    private String eng;
    private String ko;
}
