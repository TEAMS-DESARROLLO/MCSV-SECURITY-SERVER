package com.iat.security.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long idUsuario;
    private String username;
    private String nombres;
    private String registrationStatus;
    private List<Long> roles;
}