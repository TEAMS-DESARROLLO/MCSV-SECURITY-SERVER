package com.iat.security.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String username;
    private String password;
    private String nombres;
    private String registrationStatus;
    private List<Long>roles;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull( message = "Debe ingresar la fecha de expiración en formato dd-MM-yyyy" )
	private LocalDate expirationDate;
    private Integer statusUser;
    private Long idUser;

    private String file;
    private String filename;
}
