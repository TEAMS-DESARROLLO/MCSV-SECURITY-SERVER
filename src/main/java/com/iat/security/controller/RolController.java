package com.iat.security.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import org.springframework.web.bind.annotation.PutMapping;



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

    @GetMapping("/findById/{id}")
    public Rol findById(@PathVariable Long id) {
        return rolService.findById(id);
    }

    @PutMapping("update/{id}")
    public Rol putMethodName(@PathVariable Long id, @RequestBody RolRequestDto entity) {
        return rolService.update(id, entity);
    }
}
