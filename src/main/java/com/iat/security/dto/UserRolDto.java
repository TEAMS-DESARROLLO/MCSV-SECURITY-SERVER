package com.iat.security.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRolDto {

    private Long idUsuario;
    private String username;
    private String nombres;
    private String registrationStatus;
    private Integer statusUser;
    @JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate expirationDate;
    private Long idRol;
    private String rolName;
    

}
