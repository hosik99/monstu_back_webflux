package com.icetea.monstu_back.dto;

import com.icetea.monstu_back.dto.interfaceDTO.PostsDTOInterface;
import com.icetea.monstu_back.enums.State;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Stack;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostsDTO implements PostsDTOInterface {

    private Long id;

    private String author;  //memberId (FK)

    private String title;

    private String content;

    private String thumbnailUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private String category;

    private State state;

    private Boolean isPublic;

}

