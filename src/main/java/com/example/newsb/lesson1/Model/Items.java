package com.example.newsb.lesson1.Model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Data
@ToString
@NoArgsConstructor
public class Items {
    private Long id;
    private String name;
    private String description;
    private double price;
}
