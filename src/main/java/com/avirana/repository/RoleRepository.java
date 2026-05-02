package com.avirana.repository;

import com.avirana.entity.RoleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByNameAndOrganisation(String name, String org);
    List<RoleEntity> findByOrganisationAndIsActive(String org);
}
