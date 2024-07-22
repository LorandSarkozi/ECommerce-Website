package com.example.demo.controller;


import com.example.demo.dto.CategoryDto;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto){
        System.out.println("In\n");
        Category category = categoryService.createCategory(categoryDto);
        System.out.println("Created\n");
        return  ResponseEntity.status(HttpStatus.CREATED).body(category);

    }
    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(){
        System.out.println("Getting Categories\n");
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

}
