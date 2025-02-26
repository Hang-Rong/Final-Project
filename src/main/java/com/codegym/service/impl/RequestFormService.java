package com.codegym.service.impl;

import com.codegym.model.RequestForm;
import com.codegym.repository.RequestFormRepo;
import com.codegym.service.IRequestFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RequestFormService implements IRequestFormService {
    @Autowired
    private RequestFormRepo requestFormRepo;

    @Override
    public Iterable<RequestForm> findAll() {
        return requestFormRepo.findAll();
    }

    @Override
    public Optional<RequestForm> findById(Long id) {
        return requestFormRepo.findById(id);
    }

    @Override
    public void save(RequestForm requestForm) {
      requestFormRepo.save(requestForm);
    }

    @Override
    public void remove(Long id) {
        requestFormRepo.deleteById(id);

    }
}
