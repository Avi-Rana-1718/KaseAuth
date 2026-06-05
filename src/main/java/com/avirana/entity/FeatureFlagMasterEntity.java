package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "feature_flag_master")
public class FeatureFlagMasterEntity extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String name;
}
