package com.example.newsb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controler {
    @GetMapping("/")
    public String getChapter(Model model){
        return "Chapter";
    }
}
