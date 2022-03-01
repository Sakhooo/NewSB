package com.example.newsb.halykShop.repositories;

import com.example.newsb.halykShop.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UseresRepository extends JpaRepository<Users,Long> {

    Users findByEmail(String email);


}
