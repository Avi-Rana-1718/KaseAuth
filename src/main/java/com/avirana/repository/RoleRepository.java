package com.avirana.repository;

import com.avirana.dto.RoleDetailsDto;
import com.avirana.entity.RoleEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
  RoleEntity findByNameAndOrganization(String name, String org);

  List<RoleDetailsDto> findByOrganizationAndIsActiveTrue(String org);
}
