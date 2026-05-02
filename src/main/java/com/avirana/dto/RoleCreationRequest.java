package com.avirana.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RoleCreationRequest {
    private String roleName;
    private Boolean isActive;
}
