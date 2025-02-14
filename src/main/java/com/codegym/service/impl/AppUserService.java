package com.codegym.service.impl;

import com.codegym.model.AppUser;
import com.codegym.model.UserPrinciple;
import com.codegym.repository.IAppRoleRepo;
import com.codegym.repository.IAppUserRepo;
import com.codegym.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService implements IAppUserService, UserDetailsService {
    @Autowired
    private IAppUserRepo appUserRepo;

    @Autowired
    private IAppRoleRepo appRoleRepo;

    @Override
    public Iterable<AppUser> findAll() {
        return appUserRepo.findAll();
    }

    @Override
    public void setUserRole(AppUser user) {
        appUserRepo.save(user);
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return appUserRepo.findById(id);
    }

    @Override
    public void save(AppUser appUser) {
        appUserRepo.save(appUser);
    }

    @Override
    public void remove(Long id) {
        appUserRepo.deleteById(id);
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        return appUserRepo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepo.findByUsername(username)
                .map(UserPrinciple::build)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}