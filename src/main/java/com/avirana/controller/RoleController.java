package com.avirana.controller;

import com.avirana.dto.RoleCreationRequest;
import com.avirana.dto.XUserDetails;
import com.avirana.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping()
    public ResponseEntity<String> signup(@RequestBody RoleCreationRequest request, @RequestHeader("X-User-Details")XUserDetails userDetails) throws BadRequestException {
        return ResponseEntity.ok(roleService.createRole(request, userDetails));
    }

    @GetMapping()
    public ResponseEntity<List<String>> getAllRoles(@RequestHeader("X-User-Details") XUserDetails userDetails) {
        return ResponseEntity.ok(roleService.getAllRoles(userDetails.getOrg()));
    }
}
