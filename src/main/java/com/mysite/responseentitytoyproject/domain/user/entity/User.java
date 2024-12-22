package com.mysite.responseentitytoyproject.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자의 ID", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "사용자의 이름", example = "John")
    private String name;

    @Column(nullable = false, unique = true)
    @Schema(description = "사용자의 이메일", example = "John@email.com")
    private String email;

    @Column
    @Schema(description = "사용자의 전화번호", example = "010-1234-5678")
    private String phone;

    @Column(name = "created_at")
    @CreatedDate
    @Schema(description = "사용자 생성일", example = "2024-12-22 14:22:22")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Schema(description = "사용자 정보 수정일", example = "2024-12-22 14:22:23")
    private LocalDateTime updatedAt;
}
