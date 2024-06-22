package com.wanted.wanted_preonboarding_challenge_backend_20.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import com.wanted.wanted_preonboarding_challenge_backend_20.user.application.dto.JoinRequestDto;
import com.wanted.wanted_preonboarding_challenge_backend_20.user.domain.User;
import com.wanted.wanted_preonboarding_challenge_backend_20.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입에 성공한다.")
    void join_success() {
        // given
        JoinRequestDto joinRequestDto = new JoinRequestDto(
                "test@test.com",
                "password1!", "password1!",
                "활동명1"
        );

        // when
        Long joinUserId = userService.join(joinRequestDto);

        // then
        assertThat(joinUserId).isNotNull();

        User findUser = userRepository.findById(joinUserId).orElseThrow();
        assertThat(findUser.getEmail()).isEqualTo("test@test.com");
        assertThat(findUser.getName()).isEqualTo("활동명1");
    }

    @Test
    @DisplayName("이미 가입된 이메일로는 가입할 수 없다.")
    void join_fail_with_duplicate_email() {
        // given
        String email = "duplicate@test.com";

        JoinRequestDto alreadyJoinDto = new JoinRequestDto(
                email,
                "password1!", "password1!",
                "활동명1"
        );
        userService.join(alreadyJoinDto);

        // when & then
        JoinRequestDto duplicateJoinDto = new JoinRequestDto(
                email,
                "password2@", "password2@",
                "활동명2"
        );
        assertThatIllegalArgumentException().isThrownBy(
                () -> userService.join(duplicateJoinDto)
        ).withMessage("이미 가입된 이메일이 존재합니다.");
    }

    @Test
    @DisplayName("비밀번호와 확인용 비밀번호가 일치해야 한다.")
    void is_equals_to_password_and_confirm_password() {
        // given
        JoinRequestDto joinRequestDto = new JoinRequestDto(
                "join@test.com",
                "password1!", "password1@",
                "활동명1"
        );

        // when & then
        assertThatIllegalArgumentException().isThrownBy(
                () -> userService.join(joinRequestDto)
        ).withMessage("비밀번호가 일치하지 않습니다.");

    }
}