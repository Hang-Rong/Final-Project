package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;


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