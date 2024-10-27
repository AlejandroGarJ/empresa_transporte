package com.alejandro.empresa_transporte.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alejandro.empresa_transporte.Models.enums.Rol;
import com.alejandro.empresa_transporte.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    
    @Autowired
    @Lazy
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    @Lazy
    private AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
        .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authRequest -> 
                authRequest
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/camionero/create").hasAnyAuthority(Rol.CAMIONERO.name(), Rol.ADMIN.name())
                    .requestMatchers("/camioneroCamion/create").hasAnyAuthority(Rol.CAMIONERO.name(), Rol.ADMIN.name())
                    .requestMatchers("/paquete/create").hasAnyAuthority(Rol.CAMIONERO.name(), Rol.ADMIN.name())
                    .requestMatchers("/paquete/cambiarEstado").hasAnyAuthority(Rol.CAMIONERO.name(), Rol.ADMIN.name())
                    .requestMatchers("/camionero/update").hasAnyAuthority(Rol.CAMIONERO.name(), Rol.ADMIN.name())
                    .requestMatchers("/paquete/deleteById").hasAnyAuthority(Rol.CAMIONERO.name(), Rol.ADMIN.name())
                    .requestMatchers("/camion/getAll").hasAnyAuthority(Rol.CAMIONERO.name(), Rol.ADMIN.name())
                    .requestMatchers("/ciudad/getAll").hasAnyAuthority(Rol.CAMIONERO.name(), Rol.ADMIN.name())
                    .requestMatchers("/camioneroCamion/getAllByUser").hasAnyAuthority(Rol.CAMIONERO.name(), Rol.ADMIN.name())
                    .anyRequest().hasAuthority(Rol.ADMIN.name())
                    )
                .sessionManagement(sessionManager -> 
                    sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();       
    }
}
