package com.iat.security.service.impl;

import org.springframework.stereotype.Service;

import com.iat.security.model.Rol;
import com.iat.security.repository.IGenericRepository;
import com.iat.security.service.IRolService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RolServiceImpl  extends CRUDImpl<Rol,Long> implements IRolService {
    
    private final IGenericRepository<Rol,Long> repository;
   

    @Override
    protected IGenericRepository<Rol, Long> getRepositorio() {
        return repository;
    }

    
    
   /* @Override
    public RolResponseDto findById(Long id) {
        return UtilMapper.convertRolToRolResponseDto(repository.findById(id).orElse(null));   
    }

    @Autowired
    private IRolRepository repository;

    @Override
    public List<RolResponseDto> findAll() {
        return UtilMapper.convertListRolToListRolResponseDto(repository.findAll());
    }

    @Override
    public RolResponseDto save(RolRequestDto rolRequestDto) {
        return UtilMapper.convertRolToRolResponseDto(
                                        repository.save(
                                            UtilMapper.convertRolRequestDtoToRol(rolRequestDto)));
    }

    @Override
    public Page<RolResponseDto> findPaginado(Pageable pageable) {
       // return repository.findAll(pageable);
        return null;
    }

    @Override
    public RolResponseDto update(Long id, RolRequestDto request) {
       
       RolResponseDto rolResponseDto = this.findById(id);

       if(rolResponseDto!=null){

            return UtilMapper.convertRolToRolResponseDto(
                repository.save(
                    UtilMapper.convertRolRequestDtoToRol(request)));
        
        }
       
        return null;
    }
*/
}
