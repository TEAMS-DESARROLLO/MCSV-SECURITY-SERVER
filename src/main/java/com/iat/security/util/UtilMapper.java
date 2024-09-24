package com.iat.security.util;

import java.util.List;
import  java.util.stream.Collectors;

import com.iat.security.dto.RolRequestDto;
import com.iat.security.dto.RolResponseDto;
import com.iat.security.dto.UserRequestDto;
import com.iat.security.model.Rol;
import com.iat.security.model.Usuario;


public final class UtilMapper {
    
    private UtilMapper() {

	}

    public static Usuario convertUsuarioRequestDtoToUsuario(UserRequestDto userRequestDto) {
        
        if(userRequestDto==null) return null;
        
        return Usuario.builder()
                        .nombres(userRequestDto.getNombres())
                            .username(userRequestDto.getUsername())
                                .password(userRequestDto.getPassword())
                                    .build();
    }


    


    public static Rol convertRolRequestDtoToRol(RolRequestDto rolRequestDto) {
        
        if(rolRequestDto==null) return null;
        
        return Rol.builder()
                        .nombre(rolRequestDto.getNombre())
                            .descripcion(rolRequestDto.getDescripcion())
                                .build();
    }

	public static RolRequestDto convertRolToRolRequestDto(Rol rol) {
        
        if(rol==null) return null;
        
        return RolRequestDto.builder()
                                .nombre(rol.getNombre())
                                    .descripcion(rol.getDescripcion())
                                        .build();
    }

    public static RolResponseDto convertRolToRolResponseDto(Rol rol) {

        if(rol==null) return null;

        return RolResponseDto.builder()
                                .nombre(rol.getNombre())
                                    .descripcion(rol.getDescripcion())
                                        .build();
    }

    public static List<RolResponseDto> convertListRolToListRolResponseDto(List<Rol> listaRols) {

        return listaRols.stream().map(rol -> convertRolToRolResponseDto(rol))
                            .collect(Collectors.toList());


    }

}
