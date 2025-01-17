package com.codegym.service;

import com.codegym.model.Merchant;
import com.codegym.repository.IMerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MerchantService implements IMerchantService {

    private final IMerchantRepository merchantRepository;
    @Autowired
    public MerchantService(IMerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public Iterable<Merchant> findAll() {
        return merchantRepository.findAll();
    }

    @Override
    public Optional<Merchant> findById(Long id) {
        return merchantRepository.findById(id);
    }

    @Override
    public void save(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    @Override
    public void remove(Long id) {
        merchantRepository.deleteById(id);
    }
}
