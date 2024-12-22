package com.mysite.responseentitytoyproject.domain.user.dto.response;

import com.mysite.responseentitytoyproject.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final LocalDateTime createdAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.createdAt = user.getCreatedAt();
    }
}
