package com.iat.security.service.impl;


import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.iat.security.dto.AuthResponseDto;
import com.iat.security.dto.SignUpRequest;
import com.iat.security.dto.SigninRequest;
import com.iat.security.exception.AuthenticationFailedException;
import com.iat.security.exception.BussinessRuleException;
import com.iat.security.exception.ServiceException;
import com.iat.security.jwt.JwtService;
import com.iat.security.model.Usuario;
import com.iat.security.repository.IUsuarioRepository;
import com.iat.security.service.IAuthenticationService;
import com.iat.security.service.IUserService;
import com.iat.security.service.Role;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IUsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IUserService iUserService;

    @SuppressWarnings("null")
    @Override
    public AuthResponseDto signup(SignUpRequest request) {

        Usuario user = Usuario.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .nombres(request.getFirstName() )
            .role(Role.USER)
            .build();

        Usuario o = userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        //System.out.println("token : " + jwt);

        return AuthResponseDto.builder()
            .token(jwt)
            .build();
 
    }

    @Override
    public AuthResponseDto signin(SigninRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
         .orElseThrow(() -> new IllegalArgumentException("Invalid user or password."));
        //Usuario user = userRepository.findByUsername(request.getUsername()).orElse(null);
        
        if(user == null){
            return AuthResponseDto.builder().msg("El usuario no existe").status(403l) .build();
        }
        LocalDate today = LocalDate.now();
        if (user.getExpirationDate() != null && today.isAfter(user.getExpirationDate())) {
            throw new AuthenticationFailedException("La cuenta ha expirado.");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String jwt = jwtService.generateToken(user);
        jwt = "Bearer " + jwt;
        return AuthResponseDto.builder().token(jwt).status(200l).build();
    }

    @Override
    public Boolean isValidToken(String token)  {
  

            Boolean notIsExpired;
            try {
                notIsExpired = jwtService.isTokenExpired(token);
                if(notIsExpired){
                    String username=jwtService.getUsernameFromToken(token); 
                    UserDetails userDetails=iUserService.userDetailsService().loadUserByUsername(username);
                    Boolean isValid = false;
                    isValid = jwtService.isTokenValid(token, userDetails);
                    return isValid;
    
                }
            } catch (BussinessRuleException e) {
              
                throw new ServiceException("401", "Error al validar el token ",HttpStatus.UNAUTHORIZED );
                
            }
            
            return false;
   
    }
    
}
