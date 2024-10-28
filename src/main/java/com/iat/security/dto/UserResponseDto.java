package com.iat.security.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Integer statusUser;
    @JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate expirationDate;
    @JsonIgnore
    private Long idRol;
    private List<Long> roles;

    public UserResponseDto(Long idUsuario,String username,String nombres,String registrationStatus,Integer statusUser,LocalDate expirationDate,Long idRol){
        this.idUsuario = idUsuario;
        this.username = username;
        this.nombres = nombres;
        this.registrationStatus = registrationStatus;
        this.statusUser = statusUser;
        this.expirationDate = expirationDate;
        this.idRol = idRol;
    }
}