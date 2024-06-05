package com.wanted.wanted_preonboarding_challenge_backend_20.user.application.dto;

import com.wanted.wanted_preonboarding_challenge_backend_20.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinRequestDto {
    private String email;
    private String password;
    private String confirmPassword;
    private String name;

    public User toEntity() {
        return new User(
                email, password, name
        );
    }
}
