package com.iat.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "x_usuario_rol")
public class UsuarioRol {
    @Id
    @GeneratedValue(generator = "seqIdUsuarioRol",strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "seqIdUsuarioRol", sequenceName = "usuario_rol_seq", allocationSize = 1)

    @Column(name="id_x_usuario_rol")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore 
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol")
    private Rol rol;



}
