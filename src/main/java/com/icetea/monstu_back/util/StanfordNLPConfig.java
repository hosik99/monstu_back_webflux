package com.icetea.monstu_back.util;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class StanfordNLPConfig {

    @Bean
    public StanfordCoreNLP stanfordCoreNLP() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit"); //텍스트를 단어 및 문장 단위로 처리하기 위한 구성 ,pos, ner 등
        return new StanfordCoreNLP(props);
    }
}
