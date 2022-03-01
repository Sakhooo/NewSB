package com.example.newsb.halykShop.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "halyk_items")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "actual")
    private boolean actual;

    @Column(name = "amount")
    private int amount;

    @Column(name = "PictureURL")
    private String PictureURL;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories;

}
