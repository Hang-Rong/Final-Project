package com.codegym.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_account")
@Data

public class AdminAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
}
