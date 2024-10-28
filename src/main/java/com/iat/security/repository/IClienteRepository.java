package com.iat.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iat.security.model.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    
}
