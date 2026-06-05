package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "feature_flag_user_mapping")
public class FeatureFlagUserMappingEntity extends BaseEntity {
  @Column(nullable = false)
  private Integer flagId;

  @Column(nullable = false)
  private Integer userId;
}
