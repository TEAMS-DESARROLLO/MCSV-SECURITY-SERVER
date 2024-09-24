package com.iat.security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(generator = "seqRol",strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "seqRol", sequenceName = "rol_seq", allocationSize = 1)
    @Column(name="id_rol")
    private Long id;

    @Column(name="rol_name",nullable = false,unique = true)
    private String name;

    @Column(name="rol_description",nullable = false)
    private String description;
}
