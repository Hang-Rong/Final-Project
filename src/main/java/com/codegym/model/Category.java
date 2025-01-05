package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;


@Entity
@Table(name = "category")
@Data

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
}
