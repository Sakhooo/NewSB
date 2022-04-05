package com.example.newsb.halykShop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "t_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private long phoneNumber;

    @Column(name = "user_avatar")
    private String userAvatar;

    @Column(name = "country")
    private String country;

    @Column(name = "state_region")
    private String stateRegion;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;


}
