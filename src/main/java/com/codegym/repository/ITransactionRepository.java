package com.codegym.repository;

import com.codegym.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
}
