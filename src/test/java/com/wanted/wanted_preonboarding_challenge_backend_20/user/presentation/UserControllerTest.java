package com.wanted.wanted_preonboarding_challenge_backend_20.user.presentation;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.wanted_preonboarding_challenge_backend_20.user.application.UserService;
import com.wanted.wanted_preonboarding_challenge_backend_20.user.presentation.request.JoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("회원가입에 성공한다.")
    void join_success() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "test@test.com",
                "password1!", "password1!",
                "활동명1"
        );
        String requestBody = objectMapper.writeValueAsString(joinRequest);

        when(userService.join(any())).thenReturn(1L);

        // when & then
        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.message", is("회원가입에 성공했습니다.")));
    }

    @Test
    @DisplayName("회원가입 시, 아이디는 이메일 형식이어야 한다.")
    void required_id_format_email() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "test1!",
                "password1!", "password1!",
                "활동명1"
        );
        String requestBody = objectMapper.writeValueAsString(joinRequest);

        // when & then
        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("이메일 형식이 아닙니다.")));
    }

    @Test
    @DisplayName("비밀번호는 8자 이상이어야 한다.")
    void required_password_length_over_eight() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "test1@test.com",
                "passwor", "passwor",
                "활동명1"
        );
        String requestBody = objectMapper.writeValueAsString(joinRequest);

        // when & then
        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("비밀번호는 8자 이상이어야 합니다.")));
    }

    @Test
    @DisplayName("비밀번호는 16자 이상이어야 한다.")
    void password_length_under_sixteen() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "test1@test.com",
                "passwordpassword1", "passwordpassword1",
                "활동명1"
        );
        String requestBody = objectMapper.writeValueAsString(joinRequest);

        // when & then
        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("비밀번호는 16자 이하여야 합니다.")));
    }

    @Test
    @DisplayName("비밀번호는 영문대소문자를 포함해야한다.")
    void password_include_english_larger_smaller() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                "test1@test.com",
                "!@#$1234", "!@#$1234",
                "활동명1"
        );
        String requestBody = objectMapper.writeValueAsString(joinRequest);

        // when & then
        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("비밀번호는 영문대소문자/특수문자/숫자를 포함해야 합니다.")));
    }
}