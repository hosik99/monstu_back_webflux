package com.icetea.monstu_back.config.log;

import com.icetea.monstu_back.handler.log.PostsLogHandler;
import com.icetea.monstu_back.manager.log.PostLogManager;
import com.icetea.monstu_back.model.log.PostLog;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@WebFluxTest // 최소한의 WebFlux 구성만 로드
@Import(PostsLogConfig.class) // 실제 라우터 구성만 가져옴
public class PostsLogRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean // 핸들러를 목(Mock) 처리
    private PostsLogHandler handler;
    @MockBean
    private PostLogManager manager;

    @Test
    public void testGetPostLog_withMockedHandler() {
        String path = "/post/log/0/5";
        PostLog mockLog1 = new PostLog("1", "Post Title 1", 1L, 1L, 150L,
                LocalDateTime.parse("2024-11-25T20:00:00"),
                LocalDateTime.parse("2024-11-22T11:15:00"));
        PostLog mockLog2 = new PostLog("2", "Post Title 2", 2L, 2L, 250L,
                LocalDateTime.parse("2024-11-23T14:30:00"),
                LocalDateTime.parse("2024-11-23T12:00:00"));

                // Mock 핸들러가 반환할 데이터 설정
        Mockito.when(handler.getPostLog(Mockito.any()))
                .thenReturn( ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body( Flux.just( mockLog1,mockLog2 ), PostLog.class ));

        // HTTP 요청을 테스트
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("dateOption", "created_at")
                        .queryParam("dateStart", "2024-11-22T00:00")
                        .queryParam("dateEnd", "2024-11-25T23:59:59")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()     // 요청을 전송하고 응답을 받음
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].authorId").isEqualTo(1)
                .jsonPath("$[0].viewCount").isEqualTo(150)
                .jsonPath("$[1].createdAt").isEqualTo("2024-11-23T12:00:00");
    }
}

