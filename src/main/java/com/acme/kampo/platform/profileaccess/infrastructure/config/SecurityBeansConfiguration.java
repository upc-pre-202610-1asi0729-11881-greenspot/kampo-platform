package com.acme.kampo.platform.profileaccess.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Overrides Spring Boot's default UserDetailsService (which triggers
     * basic auth with a generated password). Authentication is handled
     * exclusively via JWT — no in-memory or DB user details service needed.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new org.springframework.security.core.userdetails.UsernameNotFoundException(
                    "Authentication is handled via JWT — not UserDetailsService");
        };
    }
}