package com.iat.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iat.security.dto.RolRequestDto;
import com.iat.security.model.Rol;
import com.iat.security.repository.IRolRepository;
import com.iat.security.service.IRolService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RolServiceImpl implements IRolService {

    @Override
    public Rol findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Autowired
    private IRolRepository repository;

    @Override
    public List<Rol> findAll() {
        return repository.findAll();
    }

    @Override
    public Rol save(RolRequestDto request) {
        Rol rol = new Rol();
        rol.setNombre(request.getNombre());
        rol.setDescripcion(request.getDescripcion());
        return repository.save(rol);
    }

    @Override
    public Page<Rol> findPaginado(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Rol update(Long id, RolRequestDto request) {
        // TODO Auto-generated method stub

        Rol rol = repository.findById(id).orElse(null);
        if (rol == null) {
            return null;
        }else {
            rol.setNombre(request.getNombre());
            rol.setDescripcion(request.getDescripcion());
            return repository.save(rol);
        }
        
    }

}
