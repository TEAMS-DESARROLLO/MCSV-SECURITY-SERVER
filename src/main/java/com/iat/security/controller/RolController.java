package com.iat.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iat.security.dto.RolRequestDto;
import com.iat.security.dto.RolResponseDto;
import com.iat.security.service.IRolService;
import com.iat.security.util.UtilMapper;

import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
@RequestMapping("/role")
public class RolController {
    @Autowired
    private IRolService rolService;

    @GetMapping("/findAll")
    public List<RolResponseDto> findAll() {
        return  UtilMapper.convertListRolToListRolResponseDto(rolService.getAll());
    }

    @PostMapping("/save")
    public RolResponseDto save(@RequestBody RolRequestDto rolRequestDto) {
        return UtilMapper.convertRolToRolResponseDto(
                    rolService.create(UtilMapper.convertRolRequestDtoToRol(rolRequestDto)));
    }

    @GetMapping("/findPaginado")
    public Page<RolResponseDto> findAll(Pageable pageable) {
        return null;
        //rolService.findPaginado(pageable);
    }

    @GetMapping("/findById/{id}")
    public RolResponseDto findById(@PathVariable Long id) {
        return rolService.entityById(id).map(UtilMapper::convertRolToRolResponseDto).orElse(null);
    }

    @PutMapping("update/{id}")
    public RolResponseDto putMethodName(@PathVariable Long id, @RequestBody RolRequestDto rolRequestDto) {
        return UtilMapper.convertRolToRolResponseDto(
                    rolService.update(UtilMapper.convertRolRequestDtoToRol(rolRequestDto), id));
    }
}
