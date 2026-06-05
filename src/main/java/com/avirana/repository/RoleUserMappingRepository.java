package com.avirana.repository;

import com.avirana.entity.RoleUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserMappingRepository extends JpaRepository<RoleUserMapping, Integer> {}
