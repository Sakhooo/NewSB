package com.example.newsb.halykShop.controller;

import com.example.newsb.halykShop.entities.Users;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/halyk")
public class UserController {

    @Value("${file.avatar.viewPath}")
    private String viewPath;

    @Value("${file.avatar.uploadPath}")
    private String uploadPath;

    @Value("${file.avatar.defaultPicture}")
    private String defaultPicture;

    @Autowired
    UserServices userServices;

    @PostMapping("/settingsUser")
    @PreAuthorize("isAuthenticated()")
    public String settingsUser(@RequestParam(name = "user_email") String email,
                               @RequestParam(name = "user_full_name")String fullName,
                               @RequestParam(name = "phone_number") long phoneNumber,
                               @RequestParam(name = "address") String address,
                               @RequestParam(name = "country") String country,
                               @RequestParam(name = "state") String state){
        Users user = getUserData();
        assert user != null;
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setCountry(country);
        user.setStateRegion(state);
        userServices.saveUsers(user);
        return "redirect:/halyk/profile";

    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser",getUserData());
        return "Halyk.kz/profile";
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
