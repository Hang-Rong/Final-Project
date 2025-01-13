package com.codegym.service;

import com.codegym.model.Review;
import com.codegym.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    private IReviewRepository reviewRepo;

    @Override
    public Iterable<Review> findAll() {
        return reviewRepo.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Review review) {
        reviewRepo.save(review);
    }

    @Override
    public void remove(Long id) {
        reviewRepo.deleteById(id);
    }
}
