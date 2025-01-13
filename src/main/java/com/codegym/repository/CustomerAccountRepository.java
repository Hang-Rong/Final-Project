package com.codegym.repository;

import com.codegym.model.CustomerAccount;
import com.codegym.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {
}
