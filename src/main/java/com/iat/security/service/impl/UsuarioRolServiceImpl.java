package com.iat.security.service.impl;

import org.springframework.stereotype.Service;
import com.iat.security.model.UsuarioRol;
import com.iat.security.repository.IGenericRepository;
import com.iat.security.service.IUsuarioRolService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioRolServiceImpl extends CRUDImpl<UsuarioRol,Long> implements IUsuarioRolService{

    private final IGenericRepository<UsuarioRol,Long> repository;

    @Override
    protected IGenericRepository<UsuarioRol, Long> getRepositorio() {
        return repository;
    }




}
