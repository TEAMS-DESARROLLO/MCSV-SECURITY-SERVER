package com.iat.security.mapper;

import java.util.List;

import com.iat.security.dto.UserResponseDto;
import com.iat.security.dto.UserRolDto;

public interface IUserCustomMapper {

    UserResponseDto toUserResponseDto(List<UserRolDto> userRolesDto);

}
