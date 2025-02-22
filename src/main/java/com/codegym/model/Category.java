package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;
import lombok.ToString;

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
