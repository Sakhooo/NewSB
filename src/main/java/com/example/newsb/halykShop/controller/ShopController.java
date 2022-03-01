package com.example.newsb.halykShop.controller;

import com.example.newsb.halykShop.entities.Category;
import com.example.newsb.halykShop.entities.ShopItems;
import com.example.newsb.halykShop.entities.Users;
import com.example.newsb.halykShop.services.ItemServices;
import com.example.newsb.halykShop.services.UserServices;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
@RequestMapping("/halyk")
//@RequiredArgsConstructor
public class ShopController {
    @Autowired
    private ItemServices itemServices;

    @Autowired
    private UserServices userServices;

    @Value("${file.avatar.viewPath}")
    private String viewPath;

    @Value("${file.avatar.uploadPath}")
    private String uploadPath;

    @Value("${file.avatar.defaultPicture}")
    private String defaultPicture;

    @GetMapping("/")
    public String getShop(Model model) {
        List<ShopItems> items = itemServices.getAllItem();
        List<Category> categories = itemServices.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("items", items);
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/HalykShop";
    }

    @GetMapping("/navbar")
    public String getNav(Model model) {
        List<Category> categories = itemServices.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/Layout/Navbar";
    }
//    @GetMapping("/category")
//    public String category(@RequestParam(name = "id", required = false) Long id, Model model) {
//        Category categories = itemServices.getCategory(id);
//        List<ShopItems> items = itemServices.getAllItem();
//        model.addAttribute("items", items);
//        model.addAttribute("currentUser",getUserData());
//        if (!isBlank(category)){
//
//            List<ShopItems> itemsList = itemServices.
//        }
//
//        return "Halyk.kz/HalykShop";
//    }

    @PostMapping("/addItem")
    public String addItem(@RequestParam(name = "name", defaultValue = "No Name") String name,
                          @RequestParam(name = "category_id", defaultValue = "0") Long cat_id,
                          @RequestParam(name = "description", defaultValue = "No description") String description,
                          @RequestParam(name = "price", defaultValue = "0.0") BigDecimal price,
                          @RequestParam(name = "amount", defaultValue = "0") int amount,
                          @RequestParam(name = "picture", defaultValue = "No URL") String picture) {
        Category category = itemServices.getCategory(cat_id);
        if (category != null) {
            List<Category> categories = new ArrayList<>();
            categories.add(category);
            itemServices.addItem(new ShopItems(null, name, description, price, true, amount, picture, categories));
        }
        return "redirect:/halyk/";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable(name = "id") long id) {
        ShopItems item = itemServices.getItem(id);
        List<Category> categories = itemServices.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("item", item);
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/Details";
    }
    @GetMapping("/editItem/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String editItem(Model model, @PathVariable(name = "id") long id) {
        ShopItems item = itemServices.getItem(id);
        List<Category> categories = itemServices.getAllCategory();
        categories.removeAll(item.getCategories());
        model.addAttribute("categories", categories);
        model.addAttribute("item", item);
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/editItem";
    }

    @PostMapping("/saveItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String saveItem(@RequestParam(name = "id", defaultValue = "0") Long id,
                           @RequestParam(name = "name", defaultValue = "No Name") String name,
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
        List<Category> categories = itemServices.getAllCategory();
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
        Category cat = itemServices.getCategory(catID);
        if (cat != null) {
            ShopItems item = itemServices.getItem(itemID);
            if (item != null) {
                List<Category> categories = item.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.add(cat);
                itemServices.saveItems(item);
                return "redirect:/halyk/editItem/" + itemID + "#categoriesID";
            }
        }
        return "redirect:/halyk/";
    }

    @PostMapping("/unAssigncategory")
    public String unAssigncategory(@RequestParam(name = "item_id") Long itemID,
                                 @RequestParam(name = "category_id") Long catID) {
        Category cat = itemServices.getCategory(catID);
        if (cat != null) {
            ShopItems item = itemServices.getItem(itemID);
            if (item != null) {
                List<Category> categories = item.getCategories();
                categories.remove(cat);
                itemServices.saveItems(item);
                return "redirect:/halyk/editItem/" + itemID+"#categoriesID";
            }
        }
        return "redirect:/halyk/";
    }
    @GetMapping("/403")
    public String accessDenied(Model model){
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/403";
    }
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

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/profile";
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
    @PostMapping("/uploadavatar")
    @PreAuthorize("isAuthenticated()")
    public String uploadAvatar(@RequestParam(name = "user_ava")MultipartFile file) {
        if (file.getContentType().equals("image/jpeg")||file.getContentType().equals("image/png")){
            try {
                Users currentUser = getUserData();

                String picName = DigestUtils.sha1Hex("avatar_"+currentUser.getId() + "_!Picture");

                byte [] bytes = file.getBytes();
                Path path = Paths.get(uploadPath + picName + ".jpg");
                Files.write(path,bytes);
                currentUser.setUserAvatar(picName);
                userServices.saveUsers(currentUser);
                return "redirect:/halyk/profile?success";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/halyk/";
    }

    @GetMapping(value = "/viewphoto/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte [] viewProfilePhoto(@PathVariable(name = "url") String url) throws IOException {
        String pictureURL = viewPath+defaultPicture;
        if (pictureURL!=null&&!pictureURL.equals("null")){
            pictureURL = viewPath + url + ".jpg";
        }
        InputStream in;
        try {
            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();
        } catch (Exception e) {
            ClassPathResource resource = new ClassPathResource(viewPath+defaultPicture);
            in = resource.getInputStream();
            e.printStackTrace();
        }

        return IOUtils.toByteArray(in);

    }

    @GetMapping("/gobasket")
    public String goBasket(Model model){
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/basket";
    }



    @GetMapping("/addToBasket")
    public void basket(@RequestParam(name = "id") Long id, Model model, HttpServletRequest request, HttpServletResponse response){
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
    }


}