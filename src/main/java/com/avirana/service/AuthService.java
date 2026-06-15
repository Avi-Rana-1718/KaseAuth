package com.avirana.service;

import com.avirana.dto.SigninRequest;
import com.avirana.dto.SigninResponse;
import com.avirana.dto.SignupRequest;
import com.avirana.dto.XUserDetails;
import com.avirana.entity.FeatureFlagMasterEntity;
import com.avirana.entity.FeatureFlagOrgMappingEntity;
import com.avirana.entity.RoleEntity;
import com.avirana.entity.RoleUserMappingEntity;
import com.avirana.entity.UserEntity;
import com.avirana.enums.TokenEnum;
import com.avirana.repository.FeatureFlagMasterRepository;
import com.avirana.repository.FeatureFlagOrgMappingRepository;
import com.avirana.repository.RoleRepository;
import com.avirana.repository.RoleUserMappingRepository;
import com.avirana.repository.UserRepository;
import com.avirana.util.JwtUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final RoleUserMappingRepository roleUserMappingRepository;
  private final FeatureFlagOrgMappingRepository featureFlagOrgMappingRepository;
  private final FeatureFlagMasterRepository featureFlagMasterRepository;
  private final JwtUtil jwtUtil;

  @Transactional
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

  @Transactional(readOnly = true)
  public SigninResponse signin(SigninRequest request, String org) {
    UserEntity userEntity = userRepository.findByEmailAndOrganization(request.getEmail(), org);

    if (Objects.isNull(userEntity)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User with email not found");
    }

    if (!BCrypt.checkpw(request.getPassword(), userEntity.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    Map<String, Object> claims = new HashMap<>();
    claims.put("org", org);
    claims.put("type", TokenEnum.ACCESS.getValue());

    String accessToken = jwtUtil.generateToken(userEntity.getId(), claims);
    String refreshToken = jwtUtil.generateRefreshToken(userEntity.getId());

    return new SigninResponse(accessToken, refreshToken);
  }

  @Transactional(readOnly = true)
  public SigninResponse refresh(String refreshToken) {
    refreshToken = refreshToken.replace("Bearer ", "");
    if (!jwtUtil.isRefreshTokenValid(refreshToken)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }

    Integer userId = Integer.parseInt(jwtUtil.extractSubject(refreshToken));
    String newAccessToken = jwtUtil.generateToken(userId);
    String newRefreshToken = jwtUtil.generateRefreshToken(userId);

    return new SigninResponse(newAccessToken, newRefreshToken);
  }

  @Transactional(readOnly = true)
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

    Integer userId = Integer.parseInt(jwtUtil.extractSubject(accessToken));

    UserEntity userEntity = userRepository.findByIdAndIsActiveTrue(userId);
    XUserDetails xUserDetails = new XUserDetails();
    xUserDetails.setUserId(userEntity.getId());
    xUserDetails.setOrg(userEntity.getOrganization());
    xUserDetails.setEmail(userEntity.getEmail());
    xUserDetails.setAssignedRoles(getUserRoles(userId));
    xUserDetails.setGrants(getOrgFlag(userEntity.getOrganization()));

    return xUserDetails;
  }

  private List<String> getUserRoles(Integer userId) {
    List<RoleUserMappingEntity> roleUserMappingEntityList =
        roleUserMappingRepository.findAllByUserIdAndIsActiveTrue(userId);

    List<String> resolvedRoles = new ArrayList<>();

    roleUserMappingEntityList.forEach(
        roleUserMappingEntity -> {
          Integer roleId = roleUserMappingEntity.getRoleId();
          Optional<RoleEntity> r = roleRepository.findById(roleId);

          if (r.isEmpty()) {
            log.info("Role with ID: " + roleId + " exists!");
            return;
          }

          RoleEntity roleEntity = r.get();

          String roleKey = roleEntity.getName();

          resolvedRoles.add(roleKey);
        });

    return resolvedRoles;
  }

  private List<String> getOrgFlag(String org) {
    List<FeatureFlagOrgMappingEntity> featureFlagOrgMappingEntities =
        featureFlagOrgMappingRepository.findAllByOrganizationAndIsActiveTrue(org);

    log.info("org is" + org);
    List<String> flagNames = new ArrayList<>();

    featureFlagOrgMappingEntities.forEach(
        featureFlagOrgMappingEntity -> {
          FeatureFlagMasterEntity featureFlagMasterEntity =
              featureFlagMasterRepository.findById(featureFlagOrgMappingEntity.getFlagId()).get();
          log.info(featureFlagMasterEntity.getName());
          log.info(String.valueOf(featureFlagOrgMappingEntity.getFlagId()));
          flagNames.add(featureFlagMasterEntity.getName());
        });

    return flagNames;
  }
}
