package com.iat.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iat.security.model.Usuario;

import java.util.Optional;


public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario>  findByUsername(String username);
    



}
