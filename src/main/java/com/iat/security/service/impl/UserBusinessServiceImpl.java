package com.iat.security.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.iat.security.commons.Filter;
import com.iat.security.commons.SortModel;
import com.iat.security.dto.UserRequestDto;
import com.iat.security.model.Rol;
import com.iat.security.model.Usuario;
import com.iat.security.model.UsuarioRol;
import com.iat.security.repository.IGenericRepository;
import com.iat.security.service.UserBusinessService;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class UserBusinessServiceImpl extends CRUDImpl<Usuario,Long> implements UserBusinessService {
    
     private final IGenericRepository<Usuario,Long> repository;
     ;

    @Override
    protected IGenericRepository<Usuario, Long> getRepositorio() {
        return repository;
    }

 /*    
    
      @Autowired
    private IUsuarioRepository repository;

    
  @Autowired
    private IRolService rolService; 
      
    @Autowired
    private IUsuarioRolRepository usuarioRolRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

 
 
 @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = repository.findAll();
        return usuarios;
    }

    @Transactional
    @Override
    public Usuario save(UserRequestDto request) {
        
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setNombres(request.getNombres());
        usuario.setRole(Role.ADMIN);
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario = repository.save(usuario);

        List<UsuarioRol>usuarioRolList =  new ArrayList<>();
        for (Long rolId : request.getRoles()) {
            Rol rol = rolService.findById(rolId);
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(usuario);
            usuarioRol.setRol(rol);
            usuarioRolList.add(usuarioRol);
        }

        usuarioRolRepository.saveAll(usuarioRolList);
        usuario.setRoles(usuarioRolList);
        return usuario;
    }

    @Override
    public Page<UsuarioDto> findPaginado(Pageable pageable) {
        Page<Usuario> usuarios = repository.findAll(pageable);
        return usuarios.map(this::convertToDto);
    }

    private UsuarioDto convertToDto(Usuario usuario) {
        return UsuarioDto.builder()
                .idUsuario(usuario.getIdUsuario())
                .username(usuario.getUsername())
                .nombres(usuario.getNombres())
                .role(usuario.getRole())
                .roles(usuario.getRoles().stream()  
                   .map(UsuarioRol::getRol)  
                   .collect(Collectors.toList())) 
                .build();
    }

    @Override
    public Usuario update(Long id, UserRequestDto request) {
        Usuario usuario = repository.findById(id).orElse(null);
        if (usuario == null) {
            throw new UnsupportedOperationException("Usuario no registrado");
        }else {
            usuario.setUsername(request.getUsername());
            usuario.setNombres(request.getNombres());
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));


            List<UsuarioRol>usuarioRolList =  new ArrayList<>();
            for (Long rolId : request.getRoles()) {
                Rol rol = rolService.findById(rolId);
                UsuarioRol usuarioRol = new UsuarioRol();
                usuarioRol.setUsuario(usuario);
                usuarioRol.setRol(rol);
                usuarioRolList.add(usuarioRol);
            }
            return repository.save(usuario);
        }
    }*/
}

