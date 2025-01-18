package com.codegym.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String email;
    private String address;
    private String avatarImage;

}
