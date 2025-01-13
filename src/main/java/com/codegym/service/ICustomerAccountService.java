package com.codegym.service;

import com.codegym.model.CustomerAccount;

import java.util.List;
import java.util.Optional;


public interface ICustomerAccountService {
    List<CustomerAccount> findAll();
    Optional<CustomerAccount> findById(Long id);
    CustomerAccount save(CustomerAccount customerAccount);
    void deleteById(Long id);
}
