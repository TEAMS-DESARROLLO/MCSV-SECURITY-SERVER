package com.iat.security.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iat.security.dto.UserRequestDto;
import com.iat.security.exception.ConflictException;
import com.iat.security.exception.ModelNotFoundException;
import com.iat.security.model.Rol;
import com.iat.security.model.Usuario;
import com.iat.security.model.UsuarioRol;
import com.iat.security.repository.IUsuarioRepository;
import com.iat.security.service.IRolService;
import com.iat.security.service.IUserService;
import com.iat.security.service.IUsuarioRolService;
import com.iat.security.service.UserBusinessService;
import com.iat.security.util.UtilMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUsuarioRepository iUsuarioRepository;
    private final UserBusinessService userBusinessService;
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

        request.setPassword(passwordEncoder.encode(request.getPassword()));
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
        
        /* byte[] decodeBytes = Base64.getDecoder().decode(user.getPassword());
        String userPasswordPlainText = new String(decodeBytes); */
        /* boolean samePassword = passwordEncoder.matches(request.getPassword(),user.getPassword());
        if(!samePassword){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        } */
        /* if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        } */
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
    public void deleteUsuario(Long idUser) {
        // TODO Auto-generated method stub
        Usuario user = userBusinessService.entityById(idUser).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));
        user.setRegistrationStatus("I");
        userBusinessService.update(user, idUser);
    }

}
