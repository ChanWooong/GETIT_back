package com.getit.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse{
    private String accessToken;
    private String refreshToken;
}