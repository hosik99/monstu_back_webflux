package com.icetea.monstu_back.service;

import com.icetea.monstu_back.dto.PostCreateRequest;
import com.icetea.monstu_back.model.Posts;
import com.icetea.monstu_back.model.words.SentencesEngKo;
import com.icetea.monstu_back.model.words.WordsEngKo;
import com.icetea.monstu_back.repository.PostsRepository;
import com.icetea.monstu_back.repository.SentencesEngKoRepository;
import com.icetea.monstu_back.repository.WordsEngKoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PostsService {

    private final WordsEngKoRepository wordsEngKoRps;
    private final SentencesEngKoRepository sentencesEngKoRps;

    public PostsService(WordsEngKoRepository wordsEngKoRps, SentencesEngKoRepository sentencesEngKoRps) {
        this.wordsEngKoRps = wordsEngKoRps;
        this.sentencesEngKoRps = sentencesEngKoRps;
    }

    public Mono<Boolean> save(PostCreateRequest postCreateRequest) {
        return sentencesEngKoRps.findFirstByOrderByPostIdDesc()
                .map(SentencesEngKo::getPostId)
                .defaultIfEmpty(1L)
                .flatMap(postId -> {
                    Long newPostId = postId + 1;

                    Flux<SentencesEngKo> senFlux = transToSentencesEngKo(postCreateRequest, newPostId);
                    Flux<WordsEngKo> wordFlux = transToWordEngKo(postCreateRequest, newPostId);

                    return Flux.concat(
                            senFlux.flatMap(sentencesEngKoRps::save),  // 문장 저장
                            wordFlux.flatMap(wordsEngKoRps::save)       // 단어 저장
                    ).then(Mono.just(true)); // 저장 완료 후 true 반환
                });
    }


    private Flux<SentencesEngKo> transToSentencesEngKo(PostCreateRequest postCreateRequest, Long postId) {
        List<String> oriSen = postCreateRequest.getFormSenData().getOri();
        List<String> tranSen = postCreateRequest.getFormSenData().getTran();

        if (oriSen.size() != tranSen.size())  throw new IllegalArgumentException("영어 문장과 한국어 문장의 개수가 맞지 않습니다.");

        return Flux.fromIterable(
                IntStream.range(0, oriSen.size())
                        .mapToObj(i -> {
                            SentencesEngKo sentence = new SentencesEngKo();
                            sentence.setPostId(postId);
                            sentence.setEng(oriSen.get(i));
                            sentence.setKo(tranSen.get(i));
                            return sentence;
                        })
                        .collect(Collectors.toList())
        );
    }

    private Flux<WordsEngKo> transToWordEngKo(PostCreateRequest postCreateRequest, Long postId) {
        List<String> oriWord = postCreateRequest.getFormWordData().getOri();
        List<String> tranWord = postCreateRequest.getFormWordData().getTran();

        if (oriWord.size() != tranWord.size())  throw new IllegalArgumentException("영어 단어와 한국어 단어의 개수가 맞지 않습니다.");

        return Flux.fromIterable(
                IntStream.range(0, oriWord.size())
                        .mapToObj(i -> {
                            WordsEngKo word = new WordsEngKo();
                            word.setPostId(postId);
                            word.setEng(oriWord.get(i));
                            word.setKo(tranWord.get(i));
                            return word;
                        })
                        .collect(Collectors.toList())
        );
    }

}
