package com.iat.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Datos {

    @Id
    private Long idData;
    private String descripcion;
    
}
