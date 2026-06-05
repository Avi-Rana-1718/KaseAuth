package com.avirana.controller;

import com.avirana.dto.SigninRequest;
import com.avirana.dto.SigninResponse;
import com.avirana.dto.SignupRequest;
import com.avirana.dto.XUserDetails;
import com.avirana.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(
      @Valid @RequestBody SignupRequest request,
      @NotBlank(message = "Org is mandatory") @RequestHeader("org") String org) {
    return ResponseEntity.ok(authService.signup(request, org));
  }

  @PostMapping("/signin")
  public ResponseEntity<SigninResponse> signin(
      @Valid @RequestBody SigninRequest request,
      @NotBlank(message = "Org is mandatory") @RequestHeader("org") String org) {
    return ResponseEntity.ok(authService.signin(request, org));
  }

  @PostMapping("/refresh")
  public ResponseEntity<SigninResponse> refresh(
      @NotBlank(message = "Authorization is mandatory") @RequestHeader("Authorization")
          String refreshToken) {
    log.info(refreshToken);
    return ResponseEntity.ok(authService.refresh(refreshToken));
  }

  @PostMapping("/validate")
  public ResponseEntity<XUserDetails> validate(
      @NotBlank(message = "Authorization is mandatory") @RequestHeader("Authorization")
          String accessToken) {
    return ResponseEntity.ok(authService.validate(accessToken));
  }
}
