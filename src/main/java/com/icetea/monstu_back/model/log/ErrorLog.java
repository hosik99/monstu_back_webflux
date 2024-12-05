package com.icetea.monstu_back.model.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "error_log")
public class ErrorLog {

    @Id
    private String id;

    @Field("code")
    private int code;

    @Field("user_id")
    private Long userId;

    @Field("message")
    private String message;

    @Field("endpoint")
    private String endpoint; // 예: /api/v1/resource

    @Field("http_method")
    private String httpMethod; // 예: GET, POST

//    @Field("client_ip")
//    private String clientIp;

    @Field("host_name")
    private String hostName; // 예: 서버 이름, IP 주소 등

    @Builder.Default
    @Field("timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

}
