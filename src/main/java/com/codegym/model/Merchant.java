package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "merchant")
@Data

public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String phone;
    @Column(unique = true, nullable = false)
    private String email;
    private String address;
    private String slogan;

    @OneToOne(mappedBy = "merchant", fetch = FetchType.LAZY)
    @JsonIgnore
    private AppUser appUser;

    @OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;


}
