package com.iat.security.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iat.security.dto.UserRequestDto;
import com.iat.security.dto.UserResponseDto;
import com.iat.security.dto.UserRolDto;
import com.iat.security.exception.ConflictException;
import com.iat.security.exception.ModelNotFoundException;
import com.iat.security.mapper.UserMapper;
import com.iat.security.model.Rol;
import com.iat.security.model.Usuario;
import com.iat.security.model.UsuarioRol;
import com.iat.security.repository.IUsuarioRepository;
import com.iat.security.repository.IUsuarioRolRepository;
import com.iat.security.service.IRolService;
import com.iat.security.service.IUserBusinessService;
import com.iat.security.service.IUserService;
import com.iat.security.service.IUsuarioRolService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUsuarioRepository iUsuarioRepository;
    private final IUsuarioRolRepository usuarioRolRepository;
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

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setIdUser(1L);//FIXME obtener idUser del contexto de seguridad

        Usuario user = userBusinessService.create(UserMapper.fromDto(request));


        Set<Long> usuarioRolesActualizados = new HashSet<>();

        for (Long rolId : request.getRoles()) {
            Rol rol = rolService.entityById(rolId).orElseThrow(()-> new ModelNotFoundException("Rol no encontrado"));
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(user);
            usuarioRol.setRol(rol);
            usuarioRolService.create(usuarioRol);

            usuarioRolesActualizados.add(usuarioRol.getId());
        }
        //user.setRoles(usuarioRolesActualizados);
        return user;
       
    }

    @Override
    @Transactional
    public Usuario updateUsuario(Long idUser,UserRequestDto request) {
        Usuario user = userBusinessService.entityById(idUser).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));
        user.setIdUser(1L);//FIXME obtener idUser del contexto de seguridad        
        UserMapper.updateEntityfromDto(user,request);

        userBusinessService.update(user, idUser);
        
        List<UsuarioRol> usuarioRolesActuales = usuarioRolService.findRolsByUsuarioId(idUser);

        Set<Long> usuarioRolesActualizados = new HashSet<>();

        for (UsuarioRol usuarioRol : usuarioRolesActuales) {
            usuarioRolService.delete(usuarioRol);
        }

        for (Long rolId : request.getRoles()) {
            Rol rol = rolService.entityById(rolId).orElseThrow(()-> new ModelNotFoundException("Rol no encontrado"));
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(user);
            usuarioRol.setRol(rol);
            usuarioRolService.create(usuarioRol);

            usuarioRolesActualizados.add(usuarioRol.getId());
        }

        //user.setRoles(usuarioRolesActualizados);
        return user;
    }

    @Override
    public Usuario findByUsernameIgnoreCase(String username) {
        return iUsuarioRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));
    }

    @Override
    public Usuario findById(Long idUser) {
        List<UserRolDto> userRolesDTO =  usuarioRolRepository.findUserRolByUserId(idUser);
        if( userRolesDTO == null ){
            throw new ModelNotFoundException("No hay roles relacionado al usuario id : "+idUser);
        }
        
        Usuario usuario = null;
        if( !userRolesDTO.isEmpty() ){
            usuario = UserMapper.fromRolesDtoToUser(userRolesDTO);
        }



        return usuario;
    }

    @Override
    public Usuario updateStatusUser(Long idUser, UserRequestDto request) {
        Usuario user = userBusinessService.entityById(idUser).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));    
        user.setStatusUser(request.getStatusUser());
        userBusinessService.update(user, idUser);
        return user;
    }

    @Override
    public UserResponseDto getUserResponseDto(Long idUser) {

        List<UserRolDto> userRolesDTO =  usuarioRolRepository.findUserRolByUserId(idUser);
        if( userRolesDTO == null ){
            throw new ModelNotFoundException("No hay roles relacionado al usuario id : "+idUser);
        }


        UserRolDto userRolDto = userRolesDTO.get(0);

        // crear un lamba que itere por userRolDto, y me genere una lista de roles
        List<Long> roles = userRolesDTO.stream().map(UserRolDto::getIdRol).collect(Collectors.toList());
        
        //crear una variable de UserResponseDto asignar los valores de userRolDto y en la propiedad roles asignar la lista de roles
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .idUsuario(userRolDto.getIdUsuario())
                .username(userRolDto.getUsername())
                .nombres(userRolDto.getNombres())
                .registrationStatus(userRolDto.getRegistrationStatus())
                .statusUser(userRolDto.getStatusUser())
                .expirationDate(userRolDto.getExpirationDate())
                .roles(roles)
                .file(userRolDto.getFile())
                .filename(userRolDto.getFilename())
                .build();        

        return userResponseDto;

    }

}
