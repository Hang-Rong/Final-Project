package com.codegym.service;

import com.codegym.model.Product;
import com.codegym.repository.IProductRepository;
import com.codegym.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private IReviewRepository reviewRepository;

    @Override
    public Iterable<Product> findAll() {
        return iProductRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return iProductRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        iProductRepository.save(product);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        reviewRepository.deleteByProductId(id);

        iProductRepository.deleteById(id);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return iProductRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllByNameContaining(Pageable pageable, String name) {
        return iProductRepository.findAllByNameContaining(pageable, name);
    }
}