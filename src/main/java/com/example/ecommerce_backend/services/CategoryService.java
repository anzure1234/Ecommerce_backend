package com.example.ecommerce_backend.services;

import com.example.ecommerce_backend.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);

    Category getCategoryById(Long id);

    List<Category> getAllCategory();

    Category updateCategoryById(Long id,Category category);

    void deleteCategory(Long id);

}

