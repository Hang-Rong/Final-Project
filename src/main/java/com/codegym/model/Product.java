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
    private String imageName;
    @Lob
    private String description;
    private Integer allReviews;
    private Integer allRating;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted = false;

    //trạng thái ngưng bán
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isOutOfStock = false;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_id")
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

}
