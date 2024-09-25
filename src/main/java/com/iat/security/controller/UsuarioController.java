package com.iat.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.iat.security.commons.PaginationModel;
import com.iat.security.dto.UserRequestDto;
import com.iat.security.dto.UserResponseDto;
import com.iat.security.model.Usuario;
import com.iat.security.service.IUserService;
import com.iat.security.service.UserBusinessService;
import com.iat.security.service.impl.UsuarioPaginationService;
import com.iat.security.util.UtilMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/user")
@Slf4j
public class UsuarioController {

    @Autowired
    private UserBusinessService userService;
    
    @Autowired
    private UsuarioPaginationService paginationService;

    @Autowired
    private IUserService iUserService;

    @GetMapping("/findAll")
    public List<Usuario> findAll() {
        return userService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto request) {
        UserResponseDto user = UtilMapper.convertUsuarioToUserResponseDto(iUserService.saveUsuario(request));
        return new ResponseEntity<UserResponseDto>(user, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto entity) {
        UserResponseDto user = UtilMapper.convertUsuarioToUserResponseDto(iUserService.updateUsuario(id,entity));
        return new ResponseEntity<UserResponseDto>(user, HttpStatus.OK);
    }

    @PostMapping("/pagination")
    public ResponseEntity<?> paginador(@RequestBody PaginationModel pagination ){
        Page<UserResponseDto> lst = paginationService.pagination(pagination);
        return new ResponseEntity<>(lst, HttpStatus.OK) ;
    } 

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        iUserService.deleteUsuario(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }
}
