package com.wordscreators.easyCook.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private Boolean isEmailVerified = false;
    private String password;
    private AuthProvider provider;
    private String providerId;
}
