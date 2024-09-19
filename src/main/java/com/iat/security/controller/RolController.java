package com.iat.security.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iat.security.dto.RolRequestDto;
import com.iat.security.dto.UserRequestDto;
import com.iat.security.model.Rol;
import com.iat.security.model.Usuario;
import com.iat.security.service.IRolService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/role")
public class RolController {
    @Autowired
    private IRolService rolService;

    @GetMapping("/findAll")
    public List<Rol> findAll() {
        return rolService.findAll();
    }

    @PostMapping("/save")
    public Rol save(@RequestBody RolRequestDto request) {
        return rolService.save(request);
    }

    @GetMapping("/findPaginado")
    public Page<Rol> findAll(Pageable pageable) {
        return rolService.findPaginado(pageable);
    }
}
