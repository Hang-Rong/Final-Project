package com.codegym.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="request_form")
public class RequestForm {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Date date;
    private String Reason;
    @ManyToOne
    private AppUser appUser;
    private boolean isRead = false;
}
