package com.example.ecommerce_backend.Services;

import com.example.ecommerce_backend.Models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);

    Category getCategoryById(Long id);

    List<Category> getAllCategory();

    Category updateCategoryById(Long id,Category category);

    void deleteCategory(Long id);

}

