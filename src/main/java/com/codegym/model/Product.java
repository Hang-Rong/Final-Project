package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.List;

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
    private Integer allReviews = 0;
    private Integer allRating = 0;

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

    @Lob
    private String comments;

}
