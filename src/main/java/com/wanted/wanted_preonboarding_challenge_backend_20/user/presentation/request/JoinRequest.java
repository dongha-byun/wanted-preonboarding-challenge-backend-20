package com.wanted.wanted_preonboarding_challenge_backend_20.user.presentation.request;

import com.wanted.wanted_preonboarding_challenge_backend_20.user.application.dto.JoinRequestDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinRequest {

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Min(value = 8, message = "비밀번호는 8자 이상이어야입니다.")
    @Max(value = 16, message = "비밀번호는 16자 이하여야 합니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 영문대소문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;
    private String confirmPassword;

    @NotBlank
    private String nickName;

    public JoinRequestDto toDto() {
        return new JoinRequestDto(
                email, password, confirmPassword, nickName
        );
    }
}
