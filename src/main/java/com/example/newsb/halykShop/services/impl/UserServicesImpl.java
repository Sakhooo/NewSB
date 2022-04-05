package com.example.newsb.halykShop.services.impl;

import com.example.newsb.halykShop.entities.Roles;
import com.example.newsb.halykShop.entities.Users;
import com.example.newsb.halykShop.repositories.RoleRepositories;
import com.example.newsb.halykShop.repositories.UseresRepository;
import com.example.newsb.halykShop.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UseresRepository useresRepository;

    @Autowired
    private RoleRepositories roleRepositories;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users myUser = useresRepository.findByEmail(s);
        if (myUser!=null){
            User secUser=new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
            return secUser;
        }
        throw new UsernameNotFoundException("NO User");
    }

    @Override
    public Users getUserByEmail(String email) {
        return useresRepository.findByEmail(email);
    }

    @Override
    public Users crateUser(Users user) {
        Users checkUser= useresRepository.findByEmail(user.getEmail());
        if (checkUser==null){
            Roles role = roleRepositories.findByRole("ROLE_USER");
            if (role!=null){
                ArrayList<Roles> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return useresRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public Users saveUsers(Users users) {
        return useresRepository.save(users);
    }


}
