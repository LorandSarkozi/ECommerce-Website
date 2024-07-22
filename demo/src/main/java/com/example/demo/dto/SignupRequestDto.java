package com.example.demo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupRequestDto {

    private String email;

    @Pattern(regexp = "^(?=.[A-Z])(?=.[a-z])(?=.\\d)(?=.[@#$%^&+=!*()_])[A-Za-z\\d@#$%^&+=!*()_]{8,}$",
            message = "Password must start with an uppercase letter, contain lowercase letters, at least one digit, at least one special character, and have a minimum length of 8 characters.")
    private String password;

    private String name;
}
