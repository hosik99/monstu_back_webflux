package com.icetea.monstu_back.manager;

import com.icetea.monstu_back.dto.ExtParamDTO;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
@Component
public class WordManager {

    private final StanfordCoreNLP stanfordCoreNLP;

    public WordManager(StanfordCoreNLP stanfordCoreNLP) {
        this.stanfordCoreNLP = stanfordCoreNLP;
    }

    public Mono<ArrayList<String>> extractLan(ExtParamDTO extParamDTO) {
        String text = extParamDTO.getTitle() + " " + extParamDTO.getContent();
        CoreDocument document = new CoreDocument(text);
        stanfordCoreNLP.annotate(document);

        Set<String> wordSet = new HashSet<>();

        for (CoreSentence sentence : document.sentences()) {
            for (CoreLabel token : sentence.tokens()) {
                String word = token.word();
                if (!isPunctuation(word)) {
                    wordSet.add(word);
                }
            }
        }

        return Mono.just( new ArrayList<>(wordSet) );
    }

    private boolean isPunctuation(String word) {
        return word.matches("[\\p{Punct}]+");
    }
}
