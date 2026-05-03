package com.avirana.repository;

import com.avirana.entity.FeatureFlagUserMappingEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FeatureFlagRoleMappingRepository extends JpaRepository<FeatureFlagUserMappingEntity, Integer> {
}
