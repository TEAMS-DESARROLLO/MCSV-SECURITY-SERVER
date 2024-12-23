package com.iat.security.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.iat.security.enums.RegistrationStatus;
import com.iat.security.enums.StatusUser;
import com.iat.security.service.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "x_usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String username;
    private String password;
    private String nombres;
    private String file;
    private String filename;

    // @Enumerated(EnumType.STRING) 
    // Role role;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at" ,nullable = true)
    private LocalDateTime updatedAt;

    @Column(name="registration_status ", nullable=false,length = 1)
    private String registrationStatus;

    
    //private Set<Long> roles;

    @Column(name="expiration_date" ,nullable = true)
    private LocalDate expirationDate;

    @Column(name="status_user" ,nullable = true)
    private Integer statusUser;

    @Column(name="id_user" ,nullable = true)
    private Long idUser;

    

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    //      return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    // }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @PrePersist
    public void prePersisten(){
        this.statusUser = StatusUser.ACTIVE.getValue();
        this.registrationStatus=RegistrationStatus.ACTIVE.getValue();
        this.createdAt=LocalDateTime.now();        
        
    }
    @PreUpdate
    public void preModify(){
        this.updatedAt = LocalDateTime.now();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(new SimpleGrantedAuthority("ROLE_DEFAULE"));
    }
    
}
