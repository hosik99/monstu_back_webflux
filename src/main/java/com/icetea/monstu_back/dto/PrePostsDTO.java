package com.icetea.monstu_back.dto;

import com.icetea.monstu_back.dto.interfaceDTO.PostsDTOInterface;
import com.icetea.monstu_back.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrePostsDTO implements PostsDTOInterface {

    private Long id;

    private String author;  //memberId (FK)

    private String title;

    private String thumbnailUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String category;

    private State state;

    private Boolean isPublic;
}
