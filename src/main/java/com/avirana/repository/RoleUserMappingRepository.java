package com.avirana.repository;

import com.avirana.entity.RoleUserMapping;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleUserMappingRepository extends JpaRepository<RoleUserMapping, Integer> {
}
