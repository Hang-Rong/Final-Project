package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "comment")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
    @Lob
    private String comment;
    private Date date;
    //@ManyToOne
    //@JoinColumn(name = "account_id", referencedColumnName = "id", nullable = true)
    private String author;

    public void setReviewDate(Date date) {
        this.date = date;
    }
}
