package com.codegym.service;

import com.codegym.model.Product;
import com.codegym.repository.IProductRepository;
import com.codegym.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public Iterable<Product> findAll() {
        return iProductRepository.findAllActive(PageRequest.of(0, Integer.MAX_VALUE));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return iProductRepository.findById(id).filter(product -> !product.isDeleted());
    }

    @Override
    public void save(Product product) {
        iProductRepository.save(product);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Product product = iProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setDeleted(true); // Mark as deleted
        iProductRepository.save(product);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return iProductRepository.findAllActive(pageable);
    }

    @Override
    public Page<Product> findAllByNameContaining(Pageable pageable, String name) {
        return iProductRepository.findAllByNameContaining(pageable, name);
    }
}