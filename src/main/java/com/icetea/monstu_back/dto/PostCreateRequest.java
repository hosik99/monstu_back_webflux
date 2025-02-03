package com.icetea.monstu_back.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PostCreateRequest {
    private String title;
    private String content;
    private String category;
    private FormWordData formWordData;
    private FormSenData formSenData;

    @Getter
    @Setter
    @ToString
    public static class FormWordData {
        private List<String> ori;
        private List<String> tran;

    }

    @Getter
    @Setter
    @ToString
    public static class FormSenData {
        private List<String> ori;
        private List<String> tran;

    }
}

