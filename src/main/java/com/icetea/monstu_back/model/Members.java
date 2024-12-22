package com.icetea.monstu_back.model;

import com.icetea.monstu_back.enums.Role;
import com.icetea.monstu_back.enums.Status;
import com.icetea.monstu_back.r2dbc.sqlBuilder.GenerateKClass;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@GenerateKClass
@Table("members")
public class Members {

    @Id
    private Long id;

    private String name;

    private String password;

    private Status status;

    private Role role;

    @Builder.Default
    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
