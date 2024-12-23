package com.iat.security.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iat.security.commons.PaginationModel;
import com.iat.security.dto.RolRequestDto;
import com.iat.security.dto.RolResponseDto;
import com.iat.security.enums.RegistrationStatus;
import com.iat.security.exception.ModelNotFoundException;
import com.iat.security.mapper.RolMapper;
import com.iat.security.service.IRolPaginationService;
import com.iat.security.service.IRolService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/role")
public class RolController {
    
    private final IRolService rolService;

    private final IRolPaginationService rolPaginationService;

    
    @GetMapping("/findAll")
    public List<RolResponseDto> findAll() {
        return  RolMapper.fromEntities(rolService.getAll().stream().filter(data->data.getRegistrationStatus().equals(RegistrationStatus.ACTIVE.getValue())).toList());
    }


    @PostMapping("/create")
    public ResponseEntity<RolResponseDto> save(@RequestBody RolRequestDto rolRequestDto) {
     return new ResponseEntity<>(RolMapper.fromEntity(rolService.create(RolMapper.fromDto(rolRequestDto))), HttpStatus.CREATED);
    }

    @PostMapping("/pagination")
    public ResponseEntity<?> paginador(@RequestBody PaginationModel pagination) {
        Page<?> lst = rolPaginationService.pagination(pagination);
        return new ResponseEntity<>(lst, HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public RolResponseDto findById(@PathVariable("id") Long id) {
        return rolService.entityById(id).map(RolMapper::fromEntity).orElseThrow(()->new ModelNotFoundException("Id no encontrado " + id)) ;
    }

    @PutMapping("/{id}")
    public RolResponseDto update(@PathVariable("id") Long id, @RequestBody RolRequestDto rolRequestDto) {
    
        return RolMapper.fromEntity(rolService.update(RolMapper.fromDto(rolRequestDto), id));
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RolResponseDto> delete(@PathVariable("id") Long id) {
        rolService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
