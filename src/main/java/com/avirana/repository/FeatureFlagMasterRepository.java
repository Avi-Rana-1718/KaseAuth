package com.avirana.repository;

import com.avirana.entity.FeatureFlagMasterEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FeatureFlagMasterRepository extends JpaRepository<FeatureFlagMasterEntity, Integer> {
    FeatureFlagMasterEntity findByNameAndIsActive(String name);
}
