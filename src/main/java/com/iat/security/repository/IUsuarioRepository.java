package com.iat.security.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iat.security.dto.UserRolDto;
import com.iat.security.model.Usuario;


public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario>  findByUsername(String username);
    Optional<Usuario>  findByUsernameIgnoreCase(String username);

    @Query("""
        SELECT new com.iat.security.dto.UserRolDto (u.idUsuario,u.username,u.nombres,u.registrationStatus,u.statusUser,u.expirationDate,r.id,r.name ) 
        FROM Usuario u
        INNER JOIN UsuarioRol ur ON u.idUsuario = ur.usuario.idUsuario
        INNER JOIN Rol r ON ur.rol.id = r.id
        WHERE u.idUsuario = :idUsuario
         """)
    List<UserRolDto> findUserRolByUserId(Long idUsuario);

}
