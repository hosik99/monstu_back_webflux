package com.icetea.monstu_back.model.words;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "words_eng_ko")
public class WordsEngKo {

    @Id
    private String id;

    @Field("post_id")
    private Long postId;

    @Field("Eng")
    private String eng;

    @Field("Ko")
    private String ko;
}
