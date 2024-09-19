package com.iat.security.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iat.security.model.Rol;

public interface IRolRepository extends JpaRepository<Rol,Long> {
    List<Rol> findAll();
    Page<Rol> findAll(Pageable pageable);
}
