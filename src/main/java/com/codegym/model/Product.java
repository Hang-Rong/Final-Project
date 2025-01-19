package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    @Lob
    private String imageURL;
    @Lob
    private String description;
    private Integer allReviews;
    private Integer allRating;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
