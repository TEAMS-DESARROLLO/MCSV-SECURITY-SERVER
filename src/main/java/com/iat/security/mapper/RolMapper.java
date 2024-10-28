package com.iat.security.mapper;


import java.util.List;
import java.util.stream.Collectors;

import com.iat.security.dto.RolRequestDto;
import com.iat.security.dto.RolResponseDto;
import com.iat.security.model.Rol;

public class RolMapper {

    public static RolResponseDto fromEntity(Rol rol) {
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


    public static Rol fromDto(RolRequestDto rolRequestDto) {
        if (rolRequestDto == null) {
            return null;
        }
    
        Rol.RolBuilder rolBuilder = Rol.builder()
                .name(rolRequestDto.getName())
                .description(rolRequestDto.getDescription())
                .createdAt(rolRequestDto.getCreatedAt())
                .updatedAt(rolRequestDto.getUpdatedAt());
    
        if (rolRequestDto.getId() != 0) {
            rolBuilder.id(rolRequestDto.getId())
                      .registrationStatus(rolRequestDto.getRegistrationStatus());
        }
    
        return rolBuilder.build();
    }


    public static List<RolResponseDto> fromEntities(List<Rol> listaRols) {
        return listaRols.stream()
                        .map(rol -> fromEntity(rol))
                        .collect(Collectors.toList());
    }
    
}
