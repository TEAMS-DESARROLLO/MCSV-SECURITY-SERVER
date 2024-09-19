package com.iat.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.iat.security.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;




@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig  {

    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception{

        
            http
                .csrf( scrf -> scrf.disable())
                .authorizeHttpRequests( 
                    authorize -> 
                        authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/role/**").permitAll()
                        .anyRequest().authenticated()
                );
    
            http.sessionManagement(sessionManager ->  sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            http.authenticationProvider(authProvider).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );
    
            return http.build();
            
        
                
    }
    
}
