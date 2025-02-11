package com.codegym.service.impl;

import com.codegym.model.Customer;
import com.codegym.repository.CustomerRepo;
import com.codegym.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public List<Customer> getAllCustomers() {
        return customerRepo.findByIsDeletedFalse();
    }

    @Override
    public List<Customer> getDeletedCustomers() {
        return customerRepo.findByIsDeletedTrue();
    }

    @Override
    public Iterable<Customer> findAll() {
        return customerRepo.findByIsDeletedFalse();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepo.findById(id);
    }

    @Override
    public void save(Customer customer) {
        customerRepo.save(customer);
    }

    @Override
    public void remove(Long id) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        customerOptional.ifPresent(customer -> {
            customer.setDeleted(true);
            customerRepo.save(customer);
        });
    }
}
