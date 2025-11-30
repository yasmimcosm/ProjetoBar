package com.example.demo.yasmimJose.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importação necessária
import org.springframework.security.web.SecurityFilterChain;
// Imports não utilizados removidos para clareza

@Configuration
public class SecurityConfig {

    // =======================================================
    // 1. CORREÇÃO: Método Bean para BCryptPasswordEncoder
    // =======================================================
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // =======================================================
    // 2. Configuração do FilterChain (Com correções de sintaxe)
    // =======================================================
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Desabilita o CSRF (Lambda DSL)
                .csrf(csrf -> csrf.disable())

                // Configuração da Autorização
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite todas as requisições
                )

                // Desabilita FormLogin e Logout
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable());

        return http.build();
    }
}