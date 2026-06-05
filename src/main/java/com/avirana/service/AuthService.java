package com.avirana.service;

import com.avirana.dto.SigninRequest;
import com.avirana.dto.SigninResponse;
import com.avirana.dto.SignupRequest;
import com.avirana.dto.XUserDetails;
import com.avirana.entity.UserEntity;
import com.avirana.enums.TokenEnum;
import com.avirana.repository.UserRepository;
import com.avirana.util.JwtUtil;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  public String signup(SignupRequest request, String org) {

    if (Objects.nonNull(userRepository.findByEmail(request.getEmail()))) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
    }

    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(request.getEmail());
    userEntity.setUsername(request.getUsername());
    userEntity.setOrganization(org);
    userEntity.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

    userRepository.save(userEntity);

    return "Created new user";
  }

  public SigninResponse signin(SigninRequest request, String org) {
    UserEntity userEntity = userRepository.findByEmailAndOrganization(request.getEmail(), org);

    if (Objects.isNull(userEntity)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User with email not found");
    }

    if (!BCrypt.checkpw(request.getPassword(), userEntity.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    String accessToken = jwtUtil.generateToken(request.getEmail());
    String refreshToken = jwtUtil.generateRefreshToken(request.getEmail());

    return new SigninResponse(accessToken, refreshToken);
  }

  public SigninResponse refresh(String refreshToken) {
    refreshToken = refreshToken.replace("Bearer ", "");
    if (!jwtUtil.isRefreshTokenValid(refreshToken)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }

    String subject = jwtUtil.extractSubject(refreshToken);
    String newAccessToken = jwtUtil.generateToken(subject);
    String newRefreshToken = jwtUtil.generateRefreshToken(subject);

    return new SigninResponse(newAccessToken, newRefreshToken);
  }

  public XUserDetails validate(String accessToken) {
    accessToken = accessToken.replace("Bearer ", "");
    if (!jwtUtil.isTokenValid(accessToken)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid access token");
    }

    String type = jwtUtil.extractClaim(accessToken, claims -> claims.get("type", String.class));

    if (!type.equals(TokenEnum.ACCESS.getValue())) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED, "Refresh token can't be used to access resources");
    }

    String email = jwtUtil.extractSubject(accessToken);

    UserEntity userEntity = userRepository.findByEmail(email);

    XUserDetails xUserDetails = new XUserDetails();
    xUserDetails.setEmail(email);
    xUserDetails.setUserId(userEntity.getId());
    xUserDetails.setOrg(userEntity.getOrganization());

    return xUserDetails;
  }
}
