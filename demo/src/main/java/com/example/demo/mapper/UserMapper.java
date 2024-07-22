package com.example.demo.mapper;

import com.example.demo.dto.AuthenticationRequestDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

public class UserMapper {

    public static User toEntity(AuthenticationRequestDto dto) {
        return User.builder()
                .email(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }

    public static AuthenticationRequestDto toDto(User user) {
        return AuthenticationRequestDto.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static User toCreationEntity(AuthenticationRequestDto dto) {
        return User.builder()
                .email(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }
}
