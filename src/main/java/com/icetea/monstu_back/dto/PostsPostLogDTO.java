package com.icetea.monstu_back.dto;

import com.icetea.monstu_back.model.PostLog;
import com.icetea.monstu_back.model.Posts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostsPostLogDTO {

    private PostLog postLog;
    private Posts post;

}
