package com.example.newsb.halykShop.repositories;

import com.example.newsb.halykShop.entities.Category;
import com.example.newsb.halykShop.entities.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ShopItemRepository extends JpaRepository<ShopItems,Long> {
    ShopItems findByIdAndAmountGreaterThan(Long id, int amount);
//    List<ShopItems> findByCategories(List<Category> categories);

//
//    @Query("select * from items where category = ?")
//    List<ShopItems> asd(String category);


}
