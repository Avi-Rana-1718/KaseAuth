package com.avirana.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequest {
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
