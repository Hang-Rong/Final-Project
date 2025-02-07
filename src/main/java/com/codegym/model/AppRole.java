package com.codegym.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Data
public class AppRole implements GrantedAuthority {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }

    // Constructor mặc định (nếu cần)
    public AppRole() {
    }

    // Constructor nhận tham số String name để tạo đối tượng AppRole
    public AppRole(String name) {
        this.name = name;
    }
}