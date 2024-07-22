package com.iat.security.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iat.security.model.Usuario;
import com.iat.security.repository.IUsuarioRepository;
import com.iat.security.service.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUsuarioRepository iUsuarioRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Usuario usuario = iUsuarioRepository.findByUsername(username).orElseThrow( ()-> new UsernameNotFoundException("User not found") );
                return usuario;
            }
        };
    }
    
}
