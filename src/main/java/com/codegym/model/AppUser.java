package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) //
    private ROLENAME levelOfAuthority = ROLENAME.ROLE_USER;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppRole> roll;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id",unique=true)

    private Merchant merchant;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id" , referencedColumnName = "id")
    @JsonManagedReference
    private Customer customer;

}