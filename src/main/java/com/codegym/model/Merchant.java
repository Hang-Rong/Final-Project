package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "merchant")
@Data

public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    @Column(name = "avatar_image")
    private String avatarImage;
    private String slogan;
}
