package com.iat.security.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.iat.security.model.Usuario;

import java.util.Optional;
import java.util.List;


public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario>  findByUsername(String username);
    List<Usuario> findAll();
    Page<Usuario> findAll(Pageable pageable);


}
