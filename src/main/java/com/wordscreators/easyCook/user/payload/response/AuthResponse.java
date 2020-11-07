package com.wordscreators.easyCook.user.payload.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
