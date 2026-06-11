package com.avirana.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AssignRoleRequest {
  @NotNull Integer roleId;
  @NotNull Integer userId;
}
