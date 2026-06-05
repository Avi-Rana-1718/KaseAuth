package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_user_mapping")
public class RoleUserMapping extends BaseEntity {

  @Column(nullable = false)
  private Integer roleId;

  @Column(nullable = false)
  private Integer userId;
}
