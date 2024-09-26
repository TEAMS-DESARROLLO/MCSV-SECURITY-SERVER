package com.iat.security.util;

import java.util.List;
import  java.util.stream.Collectors;

import com.iat.security.dto.RolRequestDto;
import com.iat.security.dto.RolResponseDto;
import com.iat.security.dto.UserRequestDto;
import com.iat.security.dto.UserResponseDto;
import com.iat.security.model.Rol;
import com.iat.security.model.Usuario;


public final class UtilMapper {
    
    private UtilMapper() {

	}

    public static Usuario convertUsuarioRequestDtoToUsuario(UserRequestDto userRequestDto) {
        
        if(userRequestDto==null) return null;
        
        return Usuario.builder()
                        .nombres(userRequestDto.getNames())
                            .username(userRequestDto.getUsername())
                                .password(userRequestDto.getPassword())
                                    .build();
    }


    


    public static Rol convertRolRequestDtoToRol(RolRequestDto rolRequestDto) {
        
        if(rolRequestDto==null) return null;
        
        if(rolRequestDto.getId() == 0){
            return Rol.builder()
                        .name(rolRequestDto.getName())
                            .description(rolRequestDto.getDescription())
                              .createdAt(rolRequestDto.getCreatedAt())
                                .updatedAt(rolRequestDto.getUpdatedAt())
                                      .build();
        }

            return Rol.builder()
                        .id(rolRequestDto.getId())
                        .name(rolRequestDto.getName())
                            .description(rolRequestDto.getDescription())
                              .createdAt(rolRequestDto.getCreatedAt())
                               .updatedAt(rolRequestDto.getUpdatedAt())
                                .registrationStatus(rolRequestDto.getRegistrationStatus())
                                 .build();
    }

	public static RolRequestDto convertRolToRolRequestDto(Rol rol) {
        
        if(rol==null) return null;
        
        return RolRequestDto.builder()
                                  .id(rol.getId())
                                   .name(rol.getName())
                                    .description(rol.getDescription())
                                      .createdAt(rol.getCreatedAt())
                                       .updatedAt(rol.getUpdatedAt())
                                        .build();
    }

    public static RolResponseDto convertRolToRolResponseDto(Rol rol) {
        System.out.println(">>>>>>>>" + rol);
        if(rol==null) return null;

        return RolResponseDto.builder()
                                .id(rol.getId())
                                    .name(rol.getName())
                                        .description(rol.getDescription())
                                          .createdAt(rol.getCreatedAt())
                                           .updatedAt(rol.getUpdatedAt())
                                            .registrationStatus(rol.getRegistrationStatus())
                                             .build();
    }

    public static List<RolResponseDto> convertListRolToListRolResponseDto(List<Rol> listaRols) {

        return listaRols.stream().map(rol -> convertRolToRolResponseDto(rol))
                            .collect(Collectors.toList());
    }


    public static UserResponseDto convertUsuarioToUserResponseDto(Usuario user) {

        if(user==null) return null;

        return UserResponseDto.builder()
                            .idUsuario(user.getIdUsuario())
                                .nombres(user.getNombres())
                                    .username(user.getUsername())
                                        .registrationStatus(user.getRegistrationStatus())
                                            .build();
    }
}
