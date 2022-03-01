package com.example.newsb.lesson2.BDTask;

import com.example.newsb.lesson2.modul.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class BDTask {
    public static ArrayList<Task> tasks = new ArrayList<>();
    static {
        tasks.add(
                Task.builder()
                        .id(1L)
                        .name("Lesson 1")
                        .deadlineDate("2021-11-22")
                        .description("Hello World")
                        .isCompleted(true)
                        .build()
        );
    }
    private static Long id = 2L;
    public static ArrayList<Task> getAllTask(){
        return tasks;
    }
    public static void addTask(Task task){
        task.setId(id);
        tasks.add(task);
        id++;
    }
    public static Task getId(Long id){
        for (Task task:tasks){
            if (Objects.equals(task.getId(), id)) return task;
        }
        return null;
    }
}
