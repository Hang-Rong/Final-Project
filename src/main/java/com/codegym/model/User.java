package com.codegym.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String email;
    private String address;
//    private String avatarImage;


    @OneToOne(mappedBy = "customer")
    private AppUser appUser;

    private boolean isDeleted = false;
}
