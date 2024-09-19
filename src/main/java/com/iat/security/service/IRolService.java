package com.iat.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iat.security.dto.RolRequestDto;
import com.iat.security.model.Rol;

public interface IRolService {
    List<Rol> findAll();
    Rol findById(Long id);
    Rol save(RolRequestDto request);
    Page<Rol> findPaginado(Pageable pageable);
}
