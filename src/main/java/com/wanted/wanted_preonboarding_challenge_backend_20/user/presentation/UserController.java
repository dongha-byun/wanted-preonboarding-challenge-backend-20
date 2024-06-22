package com.wanted.wanted_preonboarding_challenge_backend_20.user.presentation;

import com.wanted.wanted_preonboarding_challenge_backend_20.user.application.UserService;
import com.wanted.wanted_preonboarding_challenge_backend_20.user.presentation.request.JoinRequest;
import com.wanted.wanted_preonboarding_challenge_backend_20.user.presentation.response.JoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MultiValueMap<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        MultiValueMap<String, String> fieldErrors = new LinkedMultiValueMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> fieldErrors.add(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(fieldErrors);
    }

    @PostMapping("/join")
    public ResponseEntity<JoinResponse> join(@Validated @RequestBody JoinRequest joinRequest) {
        Long joinedId = userService.join(joinRequest.toDto());
        return ResponseEntity.ok(
                new JoinResponse(joinedId, "회원가입에 성공했습니다.")
        );
    }
}
