package com.iat.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.iat.security.dto.UserRequestDto;
import com.iat.security.model.Usuario;

public interface IUserService {

    UserDetailsService userDetailsService() ;
    List<Usuario> findAll();
    Usuario save(UserRequestDto request);
    Page<Usuario> findPaginado(Pageable pageable);
}
