package com.iat.security.service;

import java.util.List;

import com.iat.security.commons.IBaseInterfaceService;
import com.iat.security.model.UsuarioRol;

public interface IUsuarioRolService extends IBaseInterfaceService<UsuarioRol,Long>{
    List<UsuarioRol> findByUsuarioId(Long id);

    List<UsuarioRol> findRolsByUsuarioId(Long idUsuario);
}
