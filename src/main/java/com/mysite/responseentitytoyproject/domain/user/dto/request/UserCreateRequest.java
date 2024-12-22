package com.mysite.responseentitytoyproject.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    @NotBlank(message = "이름은 필수입니다")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하여야 합니다")
    private String name;

    @Email(message = "올바른 이메일 형식이 아닙니다")
    @NotBlank(message = "이메일은 필수입니다")
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$",
            message = "올바른 전화번호 형식이 아닙니다 (예: 010-1234-5678)")
    private String phone;
}
