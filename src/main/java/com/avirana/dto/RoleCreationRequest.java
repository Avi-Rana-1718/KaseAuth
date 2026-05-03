package com.avirana.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RoleCreationRequest {
    @NotBlank(message = "Role name is required")
    private String roleName;

    @NotNull(message = "isActive is required")
    private Boolean isActive;
}
