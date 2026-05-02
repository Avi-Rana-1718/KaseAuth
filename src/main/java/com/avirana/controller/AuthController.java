package com.avirana.controller;

import com.avirana.dto.SigninRequest;
import com.avirana.dto.SignupRequest;
import com.avirana.dto.SigninResponse;
import com.avirana.dto.XUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.avirana.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignupRequest request, @RequestHeader("org") String org) {
		return ResponseEntity.ok(authService.signup(request, org));
	}

	@PostMapping("/signin")
	public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest request, @RequestHeader("org") String org) {
		return ResponseEntity.ok(authService.signin(request, org));
	}

	@PostMapping("/refresh")
	public ResponseEntity<SigninResponse> refresh(@RequestHeader("Authorization") String refreshToken) {
		return ResponseEntity.ok(authService.refresh(refreshToken));
	}

	@PostMapping("/validate")
	public ResponseEntity<XUserDetails> validate(@RequestHeader("Authorization") String accessToken) {
		return ResponseEntity.ok(authService.validate(accessToken));
	}
}
