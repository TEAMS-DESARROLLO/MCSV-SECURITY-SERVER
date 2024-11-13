package com.iat.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iat.security.commons.PaginationModel;
import com.iat.security.dto.UserListResponseDto;
import com.iat.security.dto.UserRequestDto;
import com.iat.security.dto.UserResponseDto;
import com.iat.security.mapper.UserMapper;
import com.iat.security.model.Usuario;
import com.iat.security.service.IUserService;
import com.iat.security.service.impl.UsuarioPaginationService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UsuarioController {
    
    @Autowired
    private UsuarioPaginationService paginationService;

    @Autowired
    private IUserService iUserService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable("id") Long id) {
        // Usuario usuario = this.iUserService.findById(id);
        // UserResponseDto userResponseDto = UserMapper.fromEntity(usuario);

        UserResponseDto userResponseDto =iUserService.getUserResponseDto(id);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }
    

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> save(@RequestBody @Valid  UserRequestDto request) {
        UserResponseDto user = UserMapper.fromEntity(iUserService.saveUsuario(request));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto entity) {
        Usuario usuario = iUserService.updateUsuario(id,entity);
        UserResponseDto user = UserMapper.fromEntity(iUserService.updateUsuario(id,entity));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/pagination")
    public ResponseEntity<?> paginador(@RequestBody PaginationModel pagination ){
        Page<UserListResponseDto> lst = paginationService.pagination(pagination);
        return new ResponseEntity<>(lst, HttpStatus.OK) ;
    } 

    @PutMapping("/update/status-user/{id}")
    public ResponseEntity<UserResponseDto> updateStatusUser(@PathVariable Long id, @RequestBody UserRequestDto entity) {
        UserResponseDto user = UserMapper.fromEntity(iUserService.updateStatusUser(id,entity));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
