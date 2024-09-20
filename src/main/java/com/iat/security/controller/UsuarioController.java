package com.iat.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iat.security.commons.PaginationModel;
import com.iat.security.dto.UserRequestDto;
import com.iat.security.dto.UsuarioDto;
import com.iat.security.model.Rol;
import com.iat.security.model.Usuario;
import com.iat.security.service.IUserService;
import com.iat.security.service.impl.UsuarioPaginationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/user")
@Slf4j
public class UsuarioController {

    @Autowired
    private IUserService userService;
    
    @Autowired
    private UsuarioPaginationService paginationService;

    @GetMapping("/findAll")
    public List<Usuario> findAll() {
        return userService.findAll();
    }

    @PostMapping("/save")
    public Usuario save(@RequestBody UserRequestDto request) {
        return userService.save(request);
    }
    
    @GetMapping("/findPaginado")
    public Page<UsuarioDto> findAll(Pageable pageable) {
        return userService.findPaginado(pageable);
    }

    @PutMapping("update/{id}")
    public Usuario update(@PathVariable Long id, @RequestBody UserRequestDto entity) {
        return userService.update(id, entity);
    }

    @PostMapping("/pagination")
    public ResponseEntity<?> paginador(@RequestBody PaginationModel pagination ){
        Page<UsuarioDto> lst = paginationService.pagination(pagination);
        return new ResponseEntity<>(lst, HttpStatus.OK) ;
    }  
}
