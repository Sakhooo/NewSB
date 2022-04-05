package com.example.newsb.halykShop.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "halyk_items")
@Builder
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank
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
    @JoinTable(name = "item_category"
            , joinColumns = @JoinColumn(name = "items_id")
            , inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    private List<Category> categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ShopItems shopItems = (ShopItems) o;
        return id != null && Objects.equals(id, shopItems.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
