package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Customer customer;


    @OneToMany(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private List<Product> products;

    public Transaction() {}



}
