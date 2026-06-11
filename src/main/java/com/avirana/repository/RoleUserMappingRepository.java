package com.avirana.repository;

import com.avirana.entity.RoleUserMappingEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserMappingRepository extends JpaRepository<RoleUserMappingEntity, Integer> {
  List<RoleUserMappingEntity> findAllByUserIdAndIsActiveTrue(Integer id);

  RoleUserMappingEntity findByUserIdAndRoleId(Integer userId, Integer roleId);
}
