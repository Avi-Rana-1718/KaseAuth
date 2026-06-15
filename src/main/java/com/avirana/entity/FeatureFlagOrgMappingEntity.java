package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "feature_flag_org_mapping")
public class FeatureFlagOrgMappingEntity extends BaseEntity {
  @Column(nullable = false)
  private Integer flagId;

  @Column(nullable = false)
  private String organization;
}
