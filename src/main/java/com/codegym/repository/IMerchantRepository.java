package com.codegym.repository;

import com.codegym.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMerchantRepository extends JpaRepository<Merchant, Long> {
}
