package com.iat.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.iat.security.dto.UserRequestDto;
import com.iat.security.model.Usuario;

public interface IUserService{

    UserDetailsService userDetailsService() ;
    Usuario saveUsuario(UserRequestDto request);

}
