package com.example.newsb.halykShop.services;

import com.example.newsb.halykShop.entities.Category;

import java.util.List;

public interface CategoryServices {
    Category addCategory(Category category);
    List<Category> getAllCategory();
    Category getCategory(Long id);
    void deleteCategory(Category category);
    Category saveCategory(Category category);
}
