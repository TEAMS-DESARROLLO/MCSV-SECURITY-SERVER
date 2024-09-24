package com.iat.security.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iat.security.commons.IBaseInterfaceService;
import com.iat.security.model.UsuarioRol;

public interface IUsuarioRolService extends IBaseInterfaceService<UsuarioRol,Long>{
    List<UsuarioRol> findByUsuarioId(Long id);
}
