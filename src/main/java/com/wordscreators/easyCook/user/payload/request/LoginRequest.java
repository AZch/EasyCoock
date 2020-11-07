package com.wordscreators.easyCook.user.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
    // set validate email and not null
    private String email;

    // set validate not null
    private String password;
}
