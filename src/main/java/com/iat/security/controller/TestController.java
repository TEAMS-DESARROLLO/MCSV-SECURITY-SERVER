package com.iat.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    @RequestMapping("/test")
    public ResponseEntity<?> test() {

        log.info("Obteniendo el mensaje");

        var auth =  SecurityContextHolder.getContext().getAuthentication();
        log.info("Datos del Usuario: {}", auth.getPrincipal());
        log.info("Datos de los Roles {}", auth.getAuthorities());
        log.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();

        mensaje.put("contenido", "Hola Peru");
        return ResponseEntity.ok(mensaje);

    }
    
}

