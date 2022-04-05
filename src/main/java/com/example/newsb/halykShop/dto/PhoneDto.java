package com.example.newsb.halykShop.dto;

import com.example.newsb.halykShop.entities.Category;
import lombok.Data;

import java.util.List;

@Data
public class PhoneDto {
    private long id;
    private String name;
    private String description;
    private int price;
    private String pictureURL;
    private List<Category> categories;
}
