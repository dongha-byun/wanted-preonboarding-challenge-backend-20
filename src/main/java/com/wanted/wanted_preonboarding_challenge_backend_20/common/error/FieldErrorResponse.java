package com.wanted.wanted_preonboarding_challenge_backend_20.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FieldErrorResponse {
    private String fieldName;
    private String message;
}
