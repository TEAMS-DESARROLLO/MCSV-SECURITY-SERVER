package com.iat.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.iat.security.dto.UserRequestDto;
import com.iat.security.model.Rol;
import com.iat.security.model.Usuario;
import com.iat.security.model.UsuarioRol;
import com.iat.security.repository.IUsuarioRepository;
import com.iat.security.service.IRolService;
import com.iat.security.service.IUserService;
import com.iat.security.service.IUsuarioRolService;
import com.iat.security.service.UserBusinessService;
import com.iat.security.util.UtilMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUsuarioRepository iUsuarioRepository;
    private final UserBusinessService userService;
    private final IRolService rolService;
    private final IUsuarioRolService usuarioRolService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

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

    @Override
    public Usuario saveUsuario(UserRequestDto request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Usuario user = userService.create(UtilMapper.convertUsuarioRequestDtoToUsuario(request));
        for (Long rolId : request.getRoles()) {
            Rol rol = rolService.entityById(rolId).orElseThrow(()-> new RuntimeException("Rol not found"));
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(user);
            usuarioRol.setRol(rol);
            usuarioRolService.create(usuarioRol);
        }
        return user;
    }

}
