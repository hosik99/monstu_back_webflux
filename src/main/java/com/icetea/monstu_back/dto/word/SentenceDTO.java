package com.icetea.monstu_back.dto.word;

import lombok.*;

@Getter
@Builder
@ToString
public class SentenceDTO {

    private String id;

    private Long postId;

    private String sentence;
}
