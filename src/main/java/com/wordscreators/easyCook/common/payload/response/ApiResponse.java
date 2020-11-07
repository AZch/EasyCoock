package com.wordscreators.easyCook.common.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse {
    private boolean success;
    private String message;
}
