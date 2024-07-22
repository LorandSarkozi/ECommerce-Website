package com.example.demo.security;


import com.example.demo.security.filters.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {


    private final JwtRequestFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/login","/authenticate","/sign-up","/api/admin/category","/api/admin/product","/api/admin","/api/admin/dashboard","/api/admin/products","/admin/dashboard","/api/admin/search/**","/api/admin/product/**","/order/**",
                        "/api/customer/**","/api/admin/**","/socket/**","/topic/**","/app/**","/send/**").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/**")
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authorizationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }




}
