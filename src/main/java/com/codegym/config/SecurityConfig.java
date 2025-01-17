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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    //    xac thuc
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService((UserDetailsService) userService);
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public CustomSuccessHandle customSuccessHandle() {
        return new CustomSuccessHandle();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    //    phan quyen
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults())
//
//                .authorizeHttpRequests(author -> author
//                        .anyRequest().permitAll())      ;

        http
                .formLogin(formLogin -> formLogin.successHandler(customSuccessHandle())
                )
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(author -> author
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/register").permitAll()
                        .requestMatchers("/user**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN","ROLE_MERCHANT")
                        .requestMatchers("/admin**").hasRole("ADMIN")
//                        .requestMatchers("/products**", "/products/**").hasRole("USER")
//                        .requestMatchers("/shoppingcart/**", "/shoppingcart/ordernow/**", "/shoppingcart/delete/**").hasRole("USER")

                        .anyRequest().denyAll()
                )
                .exceptionHandling(customizer -> customizer.accessDeniedHandler(customAccessDeniedHandler()))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();

    }
}