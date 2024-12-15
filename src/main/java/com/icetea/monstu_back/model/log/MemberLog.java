package com.icetea.monstu_back.model.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "member_log")
public class MemberLog {

    @Id
    private String id;

    @Field("member_id")
    private Long memberId;  //memberId (postgreSQL_FK)

    @Field("failed_login_count")
    private int failedLoginCount;

    @Field("failed_login_attempts")
    private LocalDateTime failedLoginAttempts;

    @Field("last_login")
    private LocalDateTime lastLogin;

    @Builder.Default
    @Field("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
