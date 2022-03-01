package com.example.newsb.lesson1.BD;

import com.example.newsb.lesson1.Model.Items;

import java.util.ArrayList;
import java.util.List;

public class BDLesson1 {
    public static ArrayList<Items> allItems = new ArrayList<>();
    static {
        allItems.add(
                Items.builder()
                        .id(1L)
                        .name("Macbook pro 2020")
                        .description("8 GB RAM 256 GB SSD Intel Core i7")
                        .price(1200)
                        .build()
        );
        allItems.add(
                Items.builder()
                        .id(2L)
                        .name("Huaweibook pro 2021")
                        .description("16 GB RAM 516 GB SSD Intel Core i9")
                        .price(1200)
                        .build()
        );
        allItems.add(
                Items.builder()
                        .id(3L)
                        .name("Vivo pro 2021")
                        .description("4 GB RAM 126 GB SSD Intel Core i3")
                        .price(500)
                        .build()
        );
        allItems.add(
                Items.builder()
                        .id(4L)
                        .name("Honor p10 2021")
                        .description("16 GB RAM 516 GB SSD Intel Core i9")
                        .price(1250)
                        .build()
        );
    }
    private static Long id = 5L;
    public static List<Items> getItems(){
        return allItems;
    }
    public static void addItem(Items item){
        item.setId(id);
        allItems.add(item);
        id++;
    }
    public static Items getID(Long id){
        for (Items items: allItems){
            if (items.getId()==id) return items;
        }
        return null;
    }
}
