package com.avirana.service;

import com.avirana.dto.RoleCreationRequest;
import com.avirana.dto.XUserDetails;
import com.avirana.entity.RoleEntity;
import com.avirana.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public String createRole(RoleCreationRequest request, XUserDetails userDetails) throws BadRequestException {

        RoleEntity roleEntity = roleRepository.findByNameAndOrganization(request.getRoleName(), userDetails.getOrg());

        if(Objects.isNull(roleEntity)) {
            roleEntity = new RoleEntity();
            roleEntity.setName(request.getRoleName());
            roleEntity.setOrganization(userDetails.getOrg());
        }

        roleEntity.setIsActive(request.getIsActive());

        roleRepository.save(roleEntity);

        return "Successful updated role";
    }

    public List<String> getAllRoles(String org) {
        List<RoleEntity> roleEntities = roleRepository.findByOrganizationAndIsActiveTrue(org);
        List<String> roles = new ArrayList<>();

        for(RoleEntity roleEntity : roleEntities) {
            roles.add(roleEntity.getName());
        }

        return roles;
    }

}
