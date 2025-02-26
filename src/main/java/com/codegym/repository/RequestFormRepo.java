package com.codegym.repository;

import com.codegym.model.RequestForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestFormRepo extends JpaRepository<RequestForm, Long> {
}
