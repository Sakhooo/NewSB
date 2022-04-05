package com.example.newsb.halykShop.services.impl;

import com.example.newsb.halykShop.entities.Category;
import com.example.newsb.halykShop.entities.ShopItems;
import com.example.newsb.halykShop.repositories.CategoryRepository;
import com.example.newsb.halykShop.repositories.PhoneItemRepository;
import com.example.newsb.halykShop.services.ItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Locale;

@Service
public class ItemServicesImpl implements ItemServices {
    @Autowired
    private PhoneItemRepository shopItemRepository;



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
    public List<ShopItems> listAll(String keyword) {
        if (keyword!=null){
            return shopItemRepository.ListAll(keyword.toLowerCase(Locale.ROOT));
        }
        return shopItemRepository.findAll();
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
