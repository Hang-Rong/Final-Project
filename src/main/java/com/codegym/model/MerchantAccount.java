package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "merchant_account")
@Data

public class MerchantAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
}
