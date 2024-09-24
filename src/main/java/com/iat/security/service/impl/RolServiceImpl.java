package com.iat.security.service.impl;

import org.springframework.stereotype.Service;

import com.iat.security.model.Rol;
import com.iat.security.repository.IGenericRepository;
import com.iat.security.service.IRolService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RolServiceImpl  extends CRUDImpl<Rol,Long> implements IRolService {
    
    private final IGenericRepository<Rol,Long> repository;
   

    @Override
    protected IGenericRepository<Rol, Long> getRepositorio() {
        return repository;
    }

}
