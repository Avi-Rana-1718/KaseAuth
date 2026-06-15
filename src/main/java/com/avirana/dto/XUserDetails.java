package com.avirana.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XUserDetails {
  private Integer userId;
  private List<String> assignedRoles;
  private String email;
  private List<String> grants;
  private String org;
}
