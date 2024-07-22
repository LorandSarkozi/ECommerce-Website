package com.example.demo.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing category-related operations.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Creates a new category based on the provided DTO.
     *
     * @param categoryDto DTO containing category details.
     * @return Newly created Category entity.
     */
    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category);
    }

    /**
     * Retrieves all categories.
     *
     * @return List of all categories.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieves categories by name containing the given search term.
     *
     * @param name Search term for category names.
     * @return List of categories matching the search term.
     */
    public List<CategoryDto> getAllCategoriesByName(String name) {
        List<Category> categories = categoryRepository.findAllByNameContaining(name);
        return categories.stream().map(Category::getDto).collect(Collectors.toList());
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id ID of the category to delete.
     * @return True if the category was successfully deleted, false otherwise.
     */
    public boolean deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param categoryId ID of the category to retrieve.
     * @return DTO representing the category with the given ID, or null if not found.
     */
    public CategoryDto getCategoryById(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get().getDto();
        } else {
            return null;
        }
    }

    /**
     * Updates a category's details.
     *
     * @param categoryId  ID of the category to update.
     * @param categoryDto DTO containing updated category details.
     * @return DTO representing the updated category.
     * @throws IOException if an I/O error occurs while updating.
     */
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            return categoryRepository.save(category).getDto();
        } else {
            return null;
        }
    }
}
