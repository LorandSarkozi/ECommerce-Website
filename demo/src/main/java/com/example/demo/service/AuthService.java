package com.example.demo.service;

import com.example.demo.dto.AuthenticationRequestDto;
import com.example.demo.dto.SignupRequestDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.ApiExceptionResponse;
import com.example.demo.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AuthService {

    UserDto createUser(SignupRequestDto signupRequest);
    Boolean hasUserWithEmail(String email);
    void createAdmin();
    boolean deleteUser(Long id);

    List<UserDto> getAllUsers();
    UserDto getUserById(Long userId);

    UserDto updateUser(Long userId, UserDto userDto) throws IOException;

    User login(AuthenticationRequestDto dto) throws ApiExceptionResponse;
    UserDto getUserByName(String name);
    User findUserById(Long id);
    User findUserByName(String name);

    User save(User user);

    User findByEmail(String email);
}
