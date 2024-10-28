package com.iat.security.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iat.security.model.Usuario;


public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario>  findByUsername(String username);
    Optional<Usuario>  findByUsernameIgnoreCase(String username);
}
