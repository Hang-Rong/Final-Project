package com.codegym.config;

import com.codegym.controller.CustomAccessDeniedHandler;
import com.codegym.controller.CustomSuccessHandle;
import com.codegym.service.IAppRoleService;
import com.codegym.service.IAppUserService;
import com.codegym.controller.CustomAccessDeniedHandler;
import com.codegym.controller.CustomSuccessHandle;
import com.codegym.service.IAppRoleService;
import com.codegym.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableSpringDataWebSupport
public class SecurityConfig {

    @Autowired
    private IAppUserService userService;
    @Autowired
    private IAppRoleService roleService;

    // Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10); // Sử dụng BCryptPasswordEncoder cho bảo mật
    }

    // Authentication Provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService((UserDetailsService) userService);
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // Custom Success Handler
    @Bean
    public CustomSuccessHandle customSuccessHandle() {
        return new CustomSuccessHandle();
    }

    // Custom Access Denied Handler
    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    // Security Filter Chain Configuration
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(customSuccessHandle()))
                .authorizeHttpRequests(author -> author
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/register").permitAll()

                        // Các đường dẫn dành cho USER
                        .requestMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // ADMIN chỉ có quyền truy cập vào /admin**
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/products**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                        .requestMatchers("/products**", "/products/**").hasRole("ADMIN")


                        .requestMatchers("/shoppingcart/**", "/shoppingcart/ordernow/**", "/shoppingcart/delete/**").hasRole("USER")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(customizer -> customizer.accessDeniedHandler(customAccessDeniedHandler())) // Xử lý từ chối quyền truy cập
                .csrf(csrf -> csrf.disable()); // Vô hiệu hóa CSRF nếu không cần thiết

        return http.build();
    }
}
