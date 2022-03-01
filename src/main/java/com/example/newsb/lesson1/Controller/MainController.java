package com.example.newsb.lesson1.Controller;

import com.example.newsb.lesson1.BD.BDLesson1;
import com.example.newsb.lesson1.Model.Items;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lesson1")
public class MainController {
    @GetMapping("/lesson")
    public String getBitlabShop(Model model){
        List<Items> itemsList = BDLesson1.getItems();
        model.addAttribute("itemsList",itemsList);
    return "lesson1/BitlabShop";
    }
    @PostMapping("/addItem")
    public String addItem(@RequestParam(name = "name", defaultValue = "no Name") String name,
                          @RequestParam(name = "price", defaultValue = "0") int price,
                          @RequestParam(name = "description", defaultValue = "No description") String description){
        BDLesson1.addItem(new Items(null,name,description,price));
        return "redirect:/lesson1/lesson";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable(name = "id") Long id){
        Items items = BDLesson1.getID(id);
        model.addAttribute("items", items);
        return "lesson1/Details";
    }
}