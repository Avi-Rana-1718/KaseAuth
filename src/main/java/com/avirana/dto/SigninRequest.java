package com.avirana.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SigninRequest {
  @NotBlank(message = "Email is required")
  private String email;

  @NotBlank(message = "Password is mandatory")
  private String password;
}
