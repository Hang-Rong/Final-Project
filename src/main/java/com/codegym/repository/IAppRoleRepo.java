package com.codegym.repository;

import com.codegym.role.AppRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IAppRoleRepo extends CrudRepository<AppRole, Long> {
    AppRole findByName(String name);
}
