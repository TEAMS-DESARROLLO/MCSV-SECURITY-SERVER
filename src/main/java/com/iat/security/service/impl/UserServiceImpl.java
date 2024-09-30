package com.iat.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iat.security.dto.UserRequestDto;
import com.iat.security.enums.StatusUser;
import com.iat.security.exception.ConflictException;
import com.iat.security.exception.ModelNotFoundException;
import com.iat.security.model.Rol;
import com.iat.security.model.Usuario;
import com.iat.security.model.UsuarioRol;
import com.iat.security.repository.IUsuarioRepository;
import com.iat.security.service.IRolService;
import com.iat.security.service.IUserService;
import com.iat.security.service.IUsuarioRolService;
import com.iat.security.service.IUserBusinessService;
import com.iat.security.util.UtilMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUsuarioRepository iUsuarioRepository;
    private final IUserBusinessService userBusinessService;
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
    @Transactional
    public Usuario saveUsuario(UserRequestDto request) {
        if(iUsuarioRepository.findByUsernameIgnoreCase(request.getUsername().toLowerCase()).isPresent()){
            throw new ConflictException("El username ya existe");
        }

        Long idUsuario = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();        
        // Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        // Long idUsuario = 0L;
        // if (credentials instanceof Long) {
        //     idUsuario = (Long) credentials;
        // } 

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setIdUser(idUsuario);

        Usuario user = userBusinessService.create(UtilMapper.convertUsuarioRequestDtoToUsuario(request));

        List<UsuarioRol> usuarioRoles = new ArrayList<>();

        for (Long rolId : request.getRoles()) {
            Rol rol = rolService.entityById(rolId).orElseThrow(()-> new ModelNotFoundException("Rol no encontrado"));
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(user);
            usuarioRol.setRol(rol);
            usuarioRolService.create(usuarioRol);

            usuarioRoles.add(usuarioRol);
        }
        user.setUsuarioRoles(usuarioRoles);
        return user;
       
    }

    @Override
    @Transactional
    public Usuario updateUsuario(Long idUser,UserRequestDto request) {
        Usuario user = userBusinessService.entityById(idUser).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));
        user.setNombres(request.getNombres());
        user.setUsername(request.getUsername());
        user.setRegistrationStatus(request.getRegistrationStatus());
        
        Long idUsuario = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        user.setIdUser(idUsuario);
        user.setExpirationDate(request.getExpirationDate());
        user.setStatusUser(request.getStatusUser());

        userBusinessService.update(user, idUser);
        
        List<UsuarioRol> usuarioRolesActuales = usuarioRolService.findByUsuarioId(idUser);

        List<UsuarioRol> usuarioRoles = new ArrayList<>();

        for (UsuarioRol usuarioRol : usuarioRolesActuales) {
            usuarioRolService.delete(usuarioRol);
        }

        for (Long rolId : request.getRoles()) {
            Rol rol = rolService.entityById(rolId).orElseThrow(()-> new ModelNotFoundException("Rol no encontrado"));
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(user);
            usuarioRol.setRol(rol);
            usuarioRolService.create(usuarioRol);

            usuarioRoles.add(usuarioRol);
        }

        user.setUsuarioRoles(usuarioRoles);
        return user;
    }


    @Override
    public Usuario deleteUsuario(Long idUser) {
        Usuario user = userBusinessService.entityById(idUser).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));
        user.setRegistrationStatus("I");
        return userBusinessService.update(user, idUser);
    }

    @Override
    public Usuario findByUsernameIgnoreCase(String username) {
        return iUsuarioRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));
    }

}
