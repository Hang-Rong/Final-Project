package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_account")
@Data

public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
}
