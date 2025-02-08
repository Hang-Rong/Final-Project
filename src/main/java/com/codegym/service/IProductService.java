package com.codegym.service;

import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IProductService extends IGeneralService<Product> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByNameContaining(Pageable pageable, String name);
    List<Product> findByIsDeletedFalse(Pageable pageable);
    List<Product> findAllByNameContainingAndIsDeletedFalse(Pageable pageable, String name);
}