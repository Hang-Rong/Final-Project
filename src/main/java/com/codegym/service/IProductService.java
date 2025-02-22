package com.codegym.service;

import com.codegym.model.Category;
import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface IProductService extends IGeneralService<Product> {
    Page<Product> findAll(Pageable pageable);
    List<Product> findByIsDeletedFalse(Pageable pageable);
    List<Product> findAllByNameContainingAndIsDeletedFalse(Pageable pageable, String name);
    Iterable<Product> findByCategory(Category category);

}