package com.example.newsb.lesson2.Controller;

import com.example.newsb.lesson2.BDTask.BDTask;
import com.example.newsb.lesson2.modul.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/lesson2")
public class Lesson2Controller {
    @GetMapping("/tasks")
    public String getAllTask(Model model){
        ArrayList<Task> task = BDTask.getAllTask();
        model.addAttribute("task", task);
        return "lesson2/TaskManager";
    }
    @GetMapping("/getID/{id}")
    public String getID(Model model, @PathVariable(name = "id") Long id){
        Task tasks = BDTask.getId(id);
        model.addAttribute("task",tasks);
        return "lesson2/Details";
    }
}