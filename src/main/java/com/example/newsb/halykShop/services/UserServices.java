package com.example.newsb.halykShop.services;

import com.example.newsb.halykShop.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServices extends UserDetailsService {
    Users getUserByEmail(String email);
    Users crateUser(Users user);
    Users saveUsers(Users users);

}
