package com.codegym.service;

import com.codegym.model.AppUser;

import java.util.Optional;

public interface IAppUserService extends IGeneralService<AppUser> {
    void setUserRole(AppUser user);
    Optional<AppUser> findById(Long id);
    Optional<AppUser> findByUsername(String username);

}