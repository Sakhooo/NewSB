package com.example.newsb.halykShop.services.impl;

import com.example.newsb.halykShop.entities.Category;
import com.example.newsb.halykShop.repositories.CategoryRepository;
import com.example.newsb.halykShop.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServicesImpl implements CategoryServices {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
