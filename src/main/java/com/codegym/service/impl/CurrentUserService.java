package com.codegym.service.impl;

import com.codegym.model.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return (AppUser) authentication.getPrincipal();
        }

        return null;
    }


    public Long getCurrentUserId() {
        AppUser appUser = getCurrentUser();
        if (appUser != null) {
            return appUser.getId();
        }
        return null;
    }
}