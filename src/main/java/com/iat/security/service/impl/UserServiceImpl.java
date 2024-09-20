package com.iat.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iat.security.dto.UserRequestDto;
import com.iat.security.dto.UsuarioDto;
import com.iat.security.model.Usuario;
import com.iat.security.repository.IUsuarioRepository;
import com.iat.security.service.IUserService;
import com.iat.security.service.UserBusinessService;
import com.iat.security.util.UtilMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUsuarioRepository iUsuarioRepository;

   @Autowired
   private UserBusinessService userBusinessService;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Usuario usuario = iUsuarioRepository.findByUsername(username).orElseThrow( ()-> new UsernameNotFoundException("User not found") );
                return usuario;
            }
        };
    }

    @Override
    public List<Usuario> findAll() {
       return userBusinessService.getAll();
    }

    @Override
    public Usuario save(UserRequestDto request) {
        return userBusinessService.create(UtilMapper.convertUsuarioRequestDtoToUsuario(request));
    }

    @Override
    public Usuario update(Long id, UserRequestDto request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Page<UsuarioDto> findPaginado(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPaginado'");
    }

   
    
}
