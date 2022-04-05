package com.example.newsb.halykShop.controller;

import com.example.newsb.halykShop.entities.Users;
import com.example.newsb.halykShop.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/halyk")
public class AuthController {

    @Autowired
    UserServices userServices;


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/login";
    }

    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/register";
    }
    @PostMapping("register")
    public String toregister(@RequestParam(name = "user_email") String email,
                             @RequestParam(name = "user_password") String password,
                             @RequestParam(name = "re_user_password") String rePassword,
                             @RequestParam(name = "user_full_name")String fullName){
        if (password.equals(rePassword)){
            Users user = new Users();
            user.setEmail(email);
            user.setPassword(password);
            user.setFullName(fullName);


            if (userServices.crateUser(user)!=null ){
                return "redirect:/halyk/register?success";
            }
        }
        return "redirect:/halyk/register?error";
    }
    private Users getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User) authentication.getPrincipal();
            Users myUser = userServices.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }
}
