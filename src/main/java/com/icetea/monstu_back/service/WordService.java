package com.icetea.monstu_back.service;

import com.icetea.monstu_back.dto.ExtParamDTO;
import com.icetea.monstu_back.manager.WordManager;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Map;

@Service
public class WordService {

    private final WordManager WordMng;

    public WordService(WordManager WordMng){
        this.WordMng = WordMng;
    }

    public Mono<ArrayList<String>> extractLan(ExtParamDTO extParamDTO){
        return WordMng.extractLan(extParamDTO);
    }

}
