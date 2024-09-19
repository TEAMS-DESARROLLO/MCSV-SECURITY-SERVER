package com.iat.security.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iat.security.dto.UserRequestDto;
import com.iat.security.model.Rol;
import com.iat.security.model.Usuario;
import com.iat.security.model.UsuarioRol;
import com.iat.security.repository.IRolRepository;
import com.iat.security.repository.IUsuarioRepository;
import com.iat.security.repository.IUsuarioRolRepository;
import com.iat.security.service.IRolService;
import com.iat.security.service.IUserService;
import com.iat.security.service.Role;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUsuarioRepository iUsuarioRepository;
    

    @Autowired
    private IUsuarioRepository repository;

    @Autowired
    private IRolService rolService; 
      
    @Autowired
    private IUsuarioRolRepository usuarioRolRepository;

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
    public List<Usuario> findAll() {
        List<Usuario> usuarios = repository.findAll();
        return usuarios;
    }

    @Transactional
    @Override
    public Usuario save(UserRequestDto request) {
        
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setNombres(request.getNombres());
        usuario.setRole(Role.ADMIN);
        usuario.setPassword(request.getPassword());
        usuario = repository.save(usuario);

        List<UsuarioRol>usuarioRolList =  new ArrayList<>();
        for (Long rolId : request.getRoles()) {
            Rol rol = rolService.findById(rolId);
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(usuario);
            usuarioRol.setRol(rol);
            usuarioRolList.add(usuarioRol);
        }

        usuarioRolRepository.saveAll(usuarioRolList);
        usuario.setRoles(usuarioRolList);



        return usuario;
    }

    @Override
    public Page<Usuario> findPaginado(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
}
