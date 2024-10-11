package com.iat.security.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.iat.security.dto.UserRequestDto;
import com.iat.security.dto.UserResponseDto;
import com.iat.security.dto.UserRolDto;
import com.iat.security.model.Usuario;

public class UserMapper {


     public static Usuario fromDto(UserRequestDto userRequestDto) {
        
        if(userRequestDto==null) return null;
        
        return Usuario.builder()
                        .username(userRequestDto.getUsername())
                        .password(userRequestDto.getPassword())                        
                        .nombres(userRequestDto.getNombres())                                                
                        .expirationDate(userRequestDto.getExpirationDate())
                        .statusUser(userRequestDto.getStatusUser())
                        .idUser(userRequestDto.getIdUser())
                        .file(userRequestDto.getFile())
                        .filename(userRequestDto.getFilename())
                        .build();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static UserResponseDto fromEntity(Usuario user) {

        if(user==null) return null;

        List<Long> roles = new ArrayList<>();
       if(user.getRoles() != null ){
        roles = new ArrayList(user.getRoles());
    }

        return UserResponseDto.builder()
                            .idUsuario(user.getIdUsuario())
                            .username(user.getUsername())
                            .nombres(user.getNombres())
                            .registrationStatus(user.getRegistrationStatus())
                            .expirationDate(user.getExpirationDate())
                            .statusUser(user.getStatusUser())
                            .roles(roles)
                            .file(user.getFile())
                            .filename(user.getFilename())
                            .build();
    }

    public static void updateEntityfromDto(Usuario user,UserRequestDto request){
        user.setNombres(request.getNombres());
        user.setUsername(request.getUsername());
        user.setRegistrationStatus(request.getRegistrationStatus());
        user.setIdUser(request.getIdUser());
        user.setExpirationDate(request.getExpirationDate());
        user.setStatusUser(request.getStatusUser());
        user.setFile(request.getFile());
        user.setFilename(request.getFilename());
    }

    public static Usuario fromRolesDtoToUser(List<UserRolDto> userRolesDto) {
        if( userRolesDto.isEmpty() ){
            return null;
        }
        UserRolDto userRolBase = userRolesDto.get(0);
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(userRolBase.getIdUsuario());
        usuario.setUsername(userRolBase.getUsername());
        usuario.setNombres(userRolBase.getNombres());
        usuario.setRegistrationStatus(userRolBase.getRegistrationStatus());
        usuario.setStatusUser(userRolBase.getStatusUser());
        usuario.setExpirationDate(userRolBase.getExpirationDate());
        Set<Long> roles = userRolesDto.stream()
                            .mapToLong(userRol -> userRol.getIdRol())
                            .boxed() 
                            .collect(Collectors.toSet());
        usuario.setRoles(roles);
        usuario.setFile(userRolBase.getFile());
        usuario.setFilename(userRolBase.getFilename());
        return usuario;
    }

}
