package com.codegym.service;

import com.codegym.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerService extends JpaRepository<Customer, Long> {

}
