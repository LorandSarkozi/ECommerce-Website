package com.example.demo.model;

import com.example.demo.constants.UserRole;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String password;

    private String email;

    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    public UserDto getDto(){
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setRole(role);

        return userDto;
    }



}
