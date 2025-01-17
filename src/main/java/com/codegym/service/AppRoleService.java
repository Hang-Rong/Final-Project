package com.codegym.service;

import com.codegym.repository.IAppRoleRepo;
import com.codegym.role.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AppRoleService implements IAppRoleService {
    @Autowired
    private IAppRoleRepo appRoleRepo;

    @Override
    public AppRole findByName(String name) {
        return appRoleRepo.findByName(name);
    }

    @Override
    public Iterable<AppRole> findAll() {
        return appRoleRepo.findAll();
    }

    @Override
    public Optional<AppRole> findById(Long id) {
        return appRoleRepo.findById(id);
    }

    @Override
    public void save(AppRole appRole) {
        appRoleRepo.save(appRole);
    }

    @Override
    public void remove(Long id) {
        appRoleRepo.deleteById(id);
    }
}
