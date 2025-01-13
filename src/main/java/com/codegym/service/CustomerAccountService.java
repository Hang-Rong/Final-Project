package com.codegym.service;

import com.codegym.model.CustomerAccount;
import com.codegym.repository.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerAccountService implements ICustomerAccountService {

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Override
    public List<CustomerAccount> findAll() {
        return customerAccountRepository.findAll();
    }

    @Override
    public Optional<CustomerAccount> findById(Long id) {
        return customerAccountRepository.findById(id);
    }

    @Override
    public CustomerAccount save(CustomerAccount customerAccount) {
        return customerAccountRepository.save(customerAccount);
    }

    @Override
    public void deleteById(Long id) {
        customerAccountRepository.deleteById(id);
    }
}
