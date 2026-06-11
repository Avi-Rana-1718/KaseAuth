package com.avirana.service;

import com.avirana.dto.AssignRoleRequest;
import com.avirana.dto.RoleCreationRequest;
import com.avirana.dto.RoleDetailsDto;
import com.avirana.dto.XUserDetails;
import com.avirana.entity.RoleEntity;
import com.avirana.entity.RoleUserMappingEntity;
import com.avirana.repository.RoleRepository;
import com.avirana.repository.RoleUserMappingRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;
  private final RoleUserMappingRepository roleUserMappingRepository;

  @Transactional
  public String createRole(RoleCreationRequest request, XUserDetails userDetails) {

    RoleEntity roleEntity =
        roleRepository.findByNameAndOrganization(request.getRoleName(), userDetails.getOrg());

    if (Objects.isNull(roleEntity)) {
      roleEntity = new RoleEntity();
      roleEntity.setName(request.getRoleName());
      roleEntity.setOrganization(userDetails.getOrg());
    }

    roleEntity.setIsActive(request.getIsActive());

    roleRepository.save(roleEntity);

    return "Successful updated role";
  }

  @Transactional(readOnly = true)
  public List<RoleDetailsDto> getAllRoles(String org) {
    return roleRepository.findByOrganizationAndIsActiveTrue(org);
  }

  @Transactional()
  public String assignRoles(AssignRoleRequest assignRoleRequest) {

    Integer roleId = assignRoleRequest.getRoleId();
    Integer userId = assignRoleRequest.getUserId();

    RoleUserMappingEntity roleUserMappingEntity =
        roleUserMappingRepository.findByUserIdAndRoleId(userId, roleId);

    if (Objects.nonNull(roleUserMappingEntity)) {
      return "User already has requested role";
    }

    roleUserMappingEntity = new RoleUserMappingEntity();
    roleUserMappingEntity.setRoleId(roleId);
    roleUserMappingEntity.setUserId(userId);

    roleUserMappingRepository.save(roleUserMappingEntity);

    return "Assigned role to user";
  }
}
