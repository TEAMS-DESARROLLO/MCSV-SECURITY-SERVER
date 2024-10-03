package com.iat.security.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.iat.security.dto.UserResponseDto;
import com.iat.security.dto.UserRolDto;

@Component
public class UserCustomMapperImpl implements IUserCustomMapper {

    @Override
    public UserResponseDto toUserResponseDto(List<UserRolDto> userRolesDto) {
        if( userRolesDto.isEmpty() ){
            return null;
        }
        UserRolDto userRolBase = userRolesDto.get(0);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setIdUsuario(userRolBase.getIdUsuario());
        userResponseDto.setUsername(userRolBase.getUsername());
        userResponseDto.setNombres(userRolBase.getNombres());
        userResponseDto.setRegistrationStatus(userRolBase.getRegistrationStatus());
        userResponseDto.setStatusUser(userRolBase.getStatusUser());
        userResponseDto.setExpirationDate(userRolBase.getExpirationDate());
        Set<Long> roles = userRolesDto.stream()
                               .mapToLong(userRol -> userRol.getIdRol())
                               .boxed() 
                               .collect(Collectors.toSet());
        userResponseDto.setRoles(new ArrayList<>(roles));
        return userResponseDto;
    }

}
