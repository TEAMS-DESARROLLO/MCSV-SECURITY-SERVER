package com.iat.security.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    private Long statusUser;
    @JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate expirationDate;
    private List<Long> roles;
}