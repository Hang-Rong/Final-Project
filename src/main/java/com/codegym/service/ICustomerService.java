package com.codegym.service;

import com.codegym.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {
    List<Customer> getAllCustomers();
    List<Customer> getDeletedCustomers();
}
