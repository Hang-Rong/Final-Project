package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private Date birthday;
    private String email;
    private String address;
//    private String avatarImage;


    @OneToOne(mappedBy = "customer")
    @JsonBackReference
    private AppUser appUser;




    private boolean isDeleted = false;

}
