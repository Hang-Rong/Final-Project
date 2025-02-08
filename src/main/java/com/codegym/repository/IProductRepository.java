package com.codegym.repository;

import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByNameContaining(Pageable pageable, String name);
    List<Product> findByIsDeletedFalse(Pageable pageable);
    List<Product> findAllByNameContainingAndIsDeletedFalse(String name, Pageable pageable);
}
