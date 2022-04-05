package com.example.newsb.halykShop.repositories;

import com.example.newsb.halykShop.entities.ShopItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
@Transactional
public interface PhoneItemRepository extends JpaRepository<ShopItems,Long> {
    ShopItems findByIdAndAmountGreaterThan(Long id, int amount);

    @Query("select p from ShopItems p where lower(p.name) like %?1%")
    List<ShopItems> ListAll(String keyword);



//    List<ShopItems> findByCategories(List<Category> categories);

//
//    @Query("select * from items where category = ?")
//    List<ShopItems> asd(String category);


}
