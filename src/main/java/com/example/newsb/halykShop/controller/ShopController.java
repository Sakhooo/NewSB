package com.example.newsb.halykShop.controller;

import com.example.newsb.halykShop.dto.PhoneDto;
import com.example.newsb.halykShop.entities.Category;
import com.example.newsb.halykShop.entities.ShopItems;
import com.example.newsb.halykShop.entities.Users;

import com.example.newsb.halykShop.exception_handilng.NoSuchItemException;
import com.example.newsb.halykShop.services.CategoryServices;
import com.example.newsb.halykShop.services.ItemServices;
import com.example.newsb.halykShop.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/halyk")
public class ShopController {
    @Autowired
    private ItemServices itemServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private CategoryServices categoryServices;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public String getShop(@Param("keyword") String keyword,
                          Model model) {
        List<ShopItems> items = itemServices.listAll(keyword);
        List<Category> categories = categoryServices.getAllCategory();
        List<PhoneDto> phoneDtos = items.stream().map(phoneItems -> modelMapper.map(phoneItems,PhoneDto.class)).collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("items", phoneDtos);
        model.addAttribute("currentUser",getUserData());

        return "Halyk.kz/HalykShop";
    }



    @GetMapping("/{categoryId}")
    public String getCategory(@PathVariable(name = "categoryId") long s, Model model) {
        List<Category> categories = categoryServices.getAllCategory();
        Category category = categoryServices.getCategory(s);
        List<ShopItems> shopItems = category.getPhoneItems();
        model.addAttribute("categories", categories);
        model.addAttribute("items", shopItems);
        model.addAttribute("currentUser",getUserData());

        return "Halyk.kz/HalykShop";
    }

    @GetMapping("/navbar")
    public String getNav(Model model) {
        List<Category> categories = categoryServices.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/Layout/Navbar";
    }

    @PostMapping("/addItem")
    public String addItem(@RequestParam(name = "name", defaultValue = "No Name")  String name,
                          @RequestParam(name = "category_id", defaultValue = "0") Long cat_id,
                          @RequestParam(name = "description", defaultValue = "No description") String description,
                          @RequestParam(name = "price", defaultValue = "0.0") BigDecimal price,
                          @RequestParam(name = "amount", defaultValue = "0") int amount,
                          @RequestParam(name = "picture", defaultValue = "No URL") String picture,
                          Model model) {
        Category category = categoryServices.getCategory(cat_id);

            if (category != null) {
                List<Category> categories = new ArrayList<>();
                categories.add(category);
                itemServices.addItem(new ShopItems(null, name, description, price, true, amount, picture, categories));
            }
        model.addAttribute("currentUser",getUserData());
            return "Halyk.kz/addItem";
    }


    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable(name = "id") long id) {
        ShopItems item = itemServices.getItem(id);
        List<Category> categories = categoryServices.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("item", item);
        model.addAttribute("currentUser",getUserData());
        if (item==null){
            throw new NoSuchItemException("There is no Item with ID = " + id + " int Database");
        }

        return "Halyk.kz/Details";
    }
    @GetMapping("/editItem/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String editItem(Model model, @PathVariable(name = "id") long id) {
        ShopItems item = itemServices.getItem(id);
        List<Category> categories = categoryServices.getAllCategory();
        categories.removeAll(item.getCategories());
        model.addAttribute("categories", categories);
        model.addAttribute("item", item);
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/editItem";
    }

    @PostMapping("/saveItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String saveItem(@RequestParam(name = "id", defaultValue = "0")  Long id,
                           @RequestParam(name = "name",defaultValue = "No Name") String name,
                           @RequestParam(name = "description", defaultValue = "No description") String description,
                           @RequestParam(name = "price", defaultValue = "0.0") BigDecimal price,
                           @RequestParam(name = "amount", defaultValue = "0") int amount,
                           @RequestParam(name = "picture", defaultValue = "No URL") String picture) {
            ShopItems item = itemServices.getItem(id);
            if (item != null) {
                item.setName(name);
                item.setDescription(description);
                item.setPrice(price);
                item.setAmount(amount);
                item.setPictureURL(picture);
                itemServices.saveItems(item);
            }

            return "redirect:/halyk/";
    }


    @GetMapping("/addItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String addItem(Model model) {
        List<ShopItems> items = itemServices.getAllItem();
        List<Category> categories = categoryServices.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("items", items);
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/addItem";
    }

    @PostMapping("/deleteItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String deleteItem(@RequestParam(name = "id", defaultValue = "0") Long id) {
        ShopItems item = itemServices.getItem(id);
        if (item != null) {
            itemServices.deleteItem(item);
        }
        return "redirect:/halyk/";
    }

    @PostMapping("/assigncategory")
    public String assignCategory(@RequestParam(name = "item_id") Long itemID,
                                 @RequestParam(name = "category_id") Long catID) {
        Category cat = categoryServices.getCategory(catID);
        if (cat != null) {
            ShopItems item = itemServices.getItem(itemID);
            if (item != null) {
                List<Category> categories = item.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.add(cat);
                itemServices.saveItems(item);
                return "redirect:/halyk/editItem/" + itemID ;
            }
        }
        return "redirect:/halyk/";
    }

    @PostMapping("/unAssigncategory")
    public String unAssigncategory(@RequestParam(name = "item_id") Long itemID,
                                 @RequestParam(name = "category_id") Long catID) {
        Category cat = categoryServices.getCategory(catID);
        if (cat != null) {
            ShopItems item = itemServices.getItem(itemID);
            if (item != null) {
                List<Category> categories = item.getCategories();
                categories.remove(cat);
                itemServices.saveItems(item);
                return "redirect:/halyk/editItem/" + itemID;
            }
        }
        return "redirect:/halyk/";
    }
    @GetMapping("/403")
    public String accessDenied(Model model){
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/403";
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