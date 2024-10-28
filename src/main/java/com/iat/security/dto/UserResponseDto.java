package com.iat.security.dto;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang.text.StrBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private Long idUsuario;
    private String username;
    private String nombres;
    private String registrationStatus;
    private Integer statusUser;
    @JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate expirationDate;
    private List<Long> roles;

    private String file;
    private String filename;
    

}
