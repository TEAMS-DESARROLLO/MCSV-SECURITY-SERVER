package com.iat.security.dto;

import com.iat.security.model.Rol;
import com.iat.security.service.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Long idUsuario;
    private String username;
    private String nombres;
    private Role role;
    private List<Rol>roles;
    
}
