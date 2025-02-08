package com.codegym.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class ComplaintForm {
    private String name;
    private String email;
    private String subject;
    private String message;
}
