package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "role_user_mapping")
public class RoleUserMappingEntity extends BaseEntity {

  @Column(nullable = false)
  private Integer roleId;

  @Column(nullable = false)
  private Integer userId;
}
