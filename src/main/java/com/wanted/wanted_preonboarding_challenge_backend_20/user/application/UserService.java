package com.wanted.wanted_preonboarding_challenge_backend_20.user.application;

import com.wanted.wanted_preonboarding_challenge_backend_20.user.application.dto.JoinRequestDto;
import com.wanted.wanted_preonboarding_challenge_backend_20.user.domain.User;
import com.wanted.wanted_preonboarding_challenge_backend_20.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long join(JoinRequestDto joinRequestDto) {
        boolean isExistsUser = userRepository.existsByEmail(joinRequestDto.getEmail());
        if(isExistsUser) {
            throw new IllegalArgumentException("이미 가입된 이메일이 존재합니다.");
        }

        if(!joinRequestDto.getPassword().equals(joinRequestDto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        User user = joinRequestDto.toEntity();

        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }
}
