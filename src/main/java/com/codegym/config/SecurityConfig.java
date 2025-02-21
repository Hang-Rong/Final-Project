package com.codegym.config;

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

    // Authentication Provider
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults())
//
//                .authorizeHttpRequests(author -> author
//                        .anyRequest().permitAll())      ;

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
                        .requestMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER","ROLE_MERCHANT")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN","MERCHANT")
                        .requestMatchers("/products**").hasAnyRole("USER","ADMIN","MERCHANT")
                        .requestMatchers("/products**", "/products/**").hasAnyRole("MERCHANT")
                        .requestMatchers("/merchant**", "/merchant/**").hasAnyRole("MERCHANT")

                        .requestMatchers("/shoppingcart/**", "/shoppingcart/ordernow/**", "/shoppingcart/delete/**").hasRole("USER")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(customizer -> customizer.accessDeniedHandler(customAccessDeniedHandler())) // Xử lý từ chối quyền truy cập
                .csrf(csrf -> csrf.disable()); // Vô hiệu hóa CSRF nếu không cần thiết

        return http.build();
    }
}
