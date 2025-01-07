package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> products;

}
