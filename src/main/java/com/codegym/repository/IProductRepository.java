package com.codegym.repository;

import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
   // Page<Product> findAllByNameContaining(Pageable pageable, String name);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false")
    Page<Product> findAllActive(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% AND p.isDeleted = false")
    Page<Product> findAllByNameContaining(Pageable pageable, @Param("name") String name);
}
