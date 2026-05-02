package com.avirana.dto;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String email;
    private String username;
    private String password;
}
