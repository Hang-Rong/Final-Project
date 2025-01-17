package com.codegym.repository;

import com.codegym.role.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppUserRepo extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String name);
}