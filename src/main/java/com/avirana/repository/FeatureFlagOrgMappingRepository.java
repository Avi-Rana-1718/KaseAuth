package com.avirana.repository;

import com.avirana.entity.FeatureFlagOrgMappingEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureFlagOrgMappingRepository
    extends JpaRepository<FeatureFlagOrgMappingEntity, Integer> {
  List<FeatureFlagOrgMappingEntity> findAllByOrganizationAndIsActiveTrue(String org);
}
