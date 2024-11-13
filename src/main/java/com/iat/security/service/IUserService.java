package com.iat.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.iat.security.dto.UserRequestDto;
import com.iat.security.dto.UserResponseDto;
import com.iat.security.model.Usuario;

public interface IUserService{

    UserDetailsService userDetailsService() ;
    Usuario saveUsuario(UserRequestDto request);
    Usuario updateUsuario(Long idUser, UserRequestDto request);
    Usuario findByUsernameIgnoreCase(String username);
    Usuario findById(Long idUser);
    Usuario updateStatusUser(Long idUser, UserRequestDto request);

    UserResponseDto getUserResponseDto(Long idUser);
}
