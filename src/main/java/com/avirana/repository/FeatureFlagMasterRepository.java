package com.avirana.repository;

import com.avirana.entity.FeatureFlagMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureFlagMasterRepository
    extends JpaRepository<FeatureFlagMasterEntity, Integer> {}
