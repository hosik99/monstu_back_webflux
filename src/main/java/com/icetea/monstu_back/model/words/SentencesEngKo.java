package com.icetea.monstu_back.model.words;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Document(collection = "sentences_eng_ko")
public class SentencesEngKo {

    @Id
    private String id;

    @Field("post_id")
    private Long postId;

    @Field("Eng")
    private String eng;

    @Field("Ko")
    private String ko;
}
