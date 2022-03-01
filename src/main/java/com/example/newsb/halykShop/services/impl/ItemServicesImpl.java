package com.example.newsb.halykShop.services.impl;

import com.example.newsb.halykShop.entities.Category;
import com.example.newsb.halykShop.entities.ShopItems;
import com.example.newsb.halykShop.repositories.CategoryRepository;
import com.example.newsb.halykShop.repositories.ShopItemRepository;
import com.example.newsb.halykShop.services.ItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServicesImpl implements ItemServices {
    @Autowired
    private ShopItemRepository shopItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ShopItems addItem(ShopItems item) {
        return shopItemRepository.save(item);
    }

    @Override
    public List<ShopItems> getAllItem() {
        return shopItemRepository.findAll();
    }

    @Override
    public ShopItems getItem(Long id) {
        return shopItemRepository.findByIdAndAmountGreaterThan(id,0);
    }

    @Override
    public void deleteItem(ShopItems item) {
        shopItemRepository.delete(item);
    }

    @Override
    public ShopItems saveItems(ShopItems item) {
        return shopItemRepository.save(item);
    }

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

//    @Override
//    public List<ShopItems> getCat(List<Category> category) {
//        return shopItemRepository.findByCategories(category);
//    }

//    @Override
//    public List<ShopItems> getCatShop(List<Category> categories) {
//        return shopItemRepository.findByCategories(categories);
//    }
}
