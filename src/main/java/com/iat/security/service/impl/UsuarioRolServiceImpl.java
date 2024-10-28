package com.iat.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iat.security.model.UsuarioRol;
import com.iat.security.repository.IGenericRepository;
import com.iat.security.service.IUsuarioRolService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.Query;

@RequiredArgsConstructor
@Service
public class UsuarioRolServiceImpl extends CRUDImpl<UsuarioRol,Long> implements IUsuarioRolService{

    private final IGenericRepository<UsuarioRol,Long> repository;

    @Autowired
    private EntityManager entityManager;

    @Override
    protected IGenericRepository<UsuarioRol, Long> getRepositorio() {
        
        return repository;
    }

    @Override
    public List<UsuarioRol> findByUsuarioId(Long id) {
        String sqlSelect = "SELECT ur FROM UsuarioRol ur WHERE ur.usuario.id = :idUsuario";
        Query querySelect = entityManager.createQuery(sqlSelect);
        querySelect.setParameter("idUsuario", id);
        
        @SuppressWarnings("unchecked")
        List<UsuarioRol> lista = querySelect.getResultList();
        return lista;
    }




}
