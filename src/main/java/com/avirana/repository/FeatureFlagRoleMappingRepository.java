package com.avirana.repository;

import com.avirana.entity.FeatureFlagUserMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureFlagRoleMappingRepository
    extends JpaRepository<FeatureFlagUserMappingEntity, Integer> {}
