package com.icetea.monstu_back.dto.word;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class WordDTO {

    private String id;

    private String word;
}