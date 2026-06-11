package com.avirana.controller;

import com.avirana.dto.AssignRoleRequest;
import com.avirana.dto.RoleCreationRequest;
import com.avirana.dto.RoleDetailsDto;
import com.avirana.dto.XUserDetails;
import com.avirana.service.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @PostMapping()
  public ResponseEntity<String> roleCreation(
      @Valid @RequestBody RoleCreationRequest request,
      @NotNull(message = "X-User-Details are mandatory") @RequestHeader("X-User-Details")
          XUserDetails userDetails)
      throws BadRequestException {
    return ResponseEntity.ok(roleService.createRole(request, userDetails));
  }

  @GetMapping()
  public ResponseEntity<List<RoleDetailsDto>> getAllRoles(
      @Valid @NotNull(message = "X-User-Details are mandatory") @RequestHeader("X-User-Details")
          XUserDetails userDetails) {
    return ResponseEntity.ok(roleService.getAllRoles(userDetails.getOrg()));
  }

  @PostMapping("/assign")
  public ResponseEntity<String> assignRole(
      @Valid @RequestBody AssignRoleRequest assignRoleRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(roleService.assignRoles(assignRoleRequest));
  }
}
