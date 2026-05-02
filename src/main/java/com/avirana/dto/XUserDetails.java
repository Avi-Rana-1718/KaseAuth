package com.avirana.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class XUserDetails {
    private Integer userId;
    private String email;
    private List<Map<String, Map<String, String>>> grants;
    private String org;
}
