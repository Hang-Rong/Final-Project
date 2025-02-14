package com.codegym.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
            throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            boolean isBanned = authorities.stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_BANNED"));

            if (isBanned) {
                // 🚀 Nếu user đã ở trang login, không redirect nữa để tránh vòng lặp
                if (!request.getRequestURI().equals("/login")) {
                    response.sendRedirect("/login?error=banned");
                }
                return;
            }
        }

        // Nếu không phải lỗi từ BANNED, chuyển đến Access Denied
        response.sendRedirect("/access-denied");
    }
}