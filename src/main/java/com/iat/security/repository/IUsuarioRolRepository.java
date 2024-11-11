package com.iat.security.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iat.security.dto.UserRolDto;
import com.iat.security.model.UsuarioRol;

public interface IUsuarioRolRepository  extends IGenericRepository<UsuarioRol,Long> {

    @Query("""
        SELECT new com.iat.security.dto.UserRolDto (u.idUsuario,u.username,u.nombres,u.registrationStatus,u.statusUser,u.expirationDate,r.id,r.name,u.file,u.filename ) 
        FROM Usuario u
        INNER JOIN UsuarioRol ur ON u.idUsuario = ur.usuario.idUsuario
        INNER JOIN Rol r ON ur.rol.id = r.id
        WHERE u.idUsuario = :idUsuario
         """)
    List<UserRolDto> findUserRolByUserId(Long idUsuario);

    @Query("""
        SELECT ur FROM UsuarioRol ur WHERE ur.usuario.idUsuario = :idUsuario
         """)
    List<UsuarioRol> findRolsByUsuarioId(@Param("idUsuario")  Long idUsuario);



}
