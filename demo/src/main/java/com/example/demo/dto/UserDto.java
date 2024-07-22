package com.example.demo.dto;

import com.example.demo.constants.UserRole;
import lombok.Builder;
import lombok.Data;

import javax.management.relation.Role;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private UserRole role;
}
