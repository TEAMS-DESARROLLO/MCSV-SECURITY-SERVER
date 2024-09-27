package com.iat.security.service.impl;

import org.springframework.stereotype.Service;

import com.iat.security.model.Usuario;
import com.iat.security.repository.IGenericRepository;
import com.iat.security.service.IUserBusinessService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserBusinessServiceImpl extends CRUDImpl<Usuario,Long> implements IUserBusinessService {
    
    private final IGenericRepository<Usuario,Long> repository;

    @Override
    protected IGenericRepository<Usuario, Long> getRepositorio() {
        return repository;
    }
    
}

