package com.example.demo.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.model.Category;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);
    List<Category> getAllCategories();

    List<CategoryDto> getAllCategoriesByName(String name);
    boolean deleteCategory(Long id);
    CategoryDto getCategoryById(Long categoryId);

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) throws IOException;
}
