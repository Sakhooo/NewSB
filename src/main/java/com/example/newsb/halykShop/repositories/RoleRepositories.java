package com.example.newsb.halykShop.repositories;

import com.example.newsb.halykShop.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RoleRepositories extends JpaRepository<Roles,Long> {
    Roles findByRole(String role);


}
