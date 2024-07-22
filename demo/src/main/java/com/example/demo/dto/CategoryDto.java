package com.example.demo.dto;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class CategoryDto {

    private Long id;
    private String name;
    private String description;
}
