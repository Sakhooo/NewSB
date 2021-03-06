package com.example.newsb.halykShop.services;

import com.example.newsb.halykShop.entities.Category;
import com.example.newsb.halykShop.entities.ShopItems;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemServices {
    ShopItems addItem(ShopItems item);
    List<ShopItems> getAllItem();
    ShopItems getItem(Long id);
    void deleteItem(ShopItems item);
    ShopItems saveItems(ShopItems item);
    List<ShopItems> listAll(String keyword);




//    List<ShopItems> getCat(List<Category> category);
//    List<ShopItems> getCatShop(List<Category> categories);

}
