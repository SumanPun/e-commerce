package com.webApp.ecommerce.Config;
import com.webApp.ecommerce.Security.JwtAuthenticationEntryPoint;
import com.webApp.ecommerce.Security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;

    private final JwtAuthenticationEntryPoint point;
    private final JwtAuthenticationFilter filter;

    private static final String[] WHITELIST_PATTERNS = {"/auth/login"};

    public SecurityConfig(CustomUserDetailService customUserDetailService, JwtAuthenticationFilter filter, JwtAuthenticationEntryPoint point ) {
        this.customUserDetailService=customUserDetailService;
        this.filter = filter;
        this.point = point;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.
                csrf(csrf->csrf.disable())
                .authorizeHttpRequests((authorize)-> authorize
                        .requestMatchers(WHITELIST_PATTERNS).permitAll()
                        .anyRequest()
                        .permitAll() //authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
