package com.example.newsb.halykShop.controller;

import com.example.newsb.halykShop.entities.ShopItems;
import com.example.newsb.halykShop.entities.Users;
import com.example.newsb.halykShop.services.ItemServices;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/halyk")
public class BasketController {
    @Autowired
    ItemServices itemServices;

    @Autowired
    UserServices userServices;

    @PostMapping("/addToBasket")
    public String basket(@RequestParam(name = "id") Long id, Model model, HttpServletRequest request, HttpServletResponse response){
        Map<Long, ArrayList<ShopItems>> items = (Map<Long, ArrayList<ShopItems>>) request.getSession().getAttribute("item");
        if (items==null){
            items = new HashMap<>();
            request.getSession().setAttribute("item",items);
        }
        ShopItems items1 = itemServices.getItem(id);
        if (items.containsKey(id)){
            ArrayList<ShopItems> items2 = items.get(id);
            items2.add(items1);
            items.replace(items1.getId(),items2);
        }else {
            ArrayList<ShopItems> list = new ArrayList<>();
            list.add(items1);
            items.put(items1.getId(),list);
        }
        System.out.println(items1);
        request.getSession().setAttribute("item",items);
        return "redirect:/halyk/";
    }

    @PostMapping("/deleteBasket")
    public String deleteBasket(@RequestParam(name = "id") long id,HttpServletRequest request, Model model ){
        Map<Long, ArrayList<ShopItems>> items = (Map<Long, ArrayList<ShopItems>>) request.getSession().getAttribute("item");
        items.remove(id);

        return "redirect:/halyk/gobasket";
    }
    @GetMapping("/gobasket")
    public String goBasket(Model model, HttpServletRequest request){
        model.addAttribute("currentUser",getUserData());


        Map<Long, ArrayList<ShopItems>> basket = (Map<Long, ArrayList<ShopItems>>) request.getSession().getAttribute("item");
        if (basket!=null){
            ArrayList<ShopItems> items2 = new ArrayList<>();
            for (Long s: basket.keySet()){
                items2.add(itemServices.getItem(s));
            }
            model.addAttribute("basket",items2);
        }else {
            model.addAttribute("basket",basket);
        }
        return "Halyk.kz/basket";
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
