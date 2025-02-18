package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "category")
@Data

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}
