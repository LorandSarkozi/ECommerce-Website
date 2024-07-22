package com.example.demo.model;


import com.example.demo.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    public CategoryDto getDto(){
        CategoryDto categoryDto =  new CategoryDto();
        categoryDto.setName(name);
        categoryDto.setDescription(description);
        categoryDto.setId(id);

        return categoryDto;
    }


}
