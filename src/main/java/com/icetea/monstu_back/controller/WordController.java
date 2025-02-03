package com.icetea.monstu_back.controller;

import com.icetea.monstu_back.dto.ExtParamDTO;
import com.icetea.monstu_back.dto.PostCreateRequest;
import com.icetea.monstu_back.model.words.SentencesEngKo;
import com.icetea.monstu_back.model.words.WordsEngKo;
import com.icetea.monstu_back.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/w")
public class WordController {

    private final WordService wordSvc;

    public WordController(WordService wordSvc){
        this.wordSvc = wordSvc;
    }

    // title과 content로 부터 단어,문장 추출
    @PostMapping("/ext/engko")
    public Mono<ResponseEntity<ArrayList<String>>> getExtEngKo(@RequestBody ExtParamDTO extParamDTO) {
        return wordSvc.extractLan(extParamDTO)
                .map(resultList -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new ArrayList<>(resultList)));
    }



}
