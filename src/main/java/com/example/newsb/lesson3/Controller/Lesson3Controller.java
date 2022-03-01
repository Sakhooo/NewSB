package com.example.newsb.lesson3.Controller;

import com.example.newsb.lesson2.BDTask.BDTask;
import com.example.newsb.lesson2.modul.Task;
import com.example.newsb.lesson3.Services.TestServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.ArrayList;

@Controller
@RequestMapping("/lesson3")
@RequiredArgsConstructor
public class Lesson3Controller {
    private final TestServices testServices;

    @GetMapping("/Posts")
    public String getAllPosts(Model model){
        testServices.setTitle("New Update");
        testServices.setShortContent("We update bla bla bla bla ");
        testServices.setContent("This is big content");
        String title = testServices.getTitle();
        String shortContent= testServices.getShortContent();
        String content = testServices.getContent();
        Timestamp timestamp = testServices.getPostDate();
        model.addAttribute("timestamp",timestamp);
        model.addAttribute("title",title);
        model.addAttribute("shortContent",shortContent);
        model.addAttribute("content",content);
        return "/Lesson3/University";
    }
}
