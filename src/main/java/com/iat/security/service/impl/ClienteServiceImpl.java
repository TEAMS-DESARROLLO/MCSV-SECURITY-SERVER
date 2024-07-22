package com.iat.security.service.impl;



import org.springframework.stereotype.Service;

import com.iat.security.model.Cliente;
import com.iat.security.repository.IClienteRepository;
import com.iat.security.service.IClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository iClienteRepository;

    @Override
    public Cliente Create(Cliente cliente) {
        return iClienteRepository.save(cliente);
    }

    @SuppressWarnings("null")
    @Override
    public Cliente Read(Long id) {

        return iClienteRepository.findById(id).orElseThrow( null );
    }

    @SuppressWarnings("unused")
    @Override
    public Cliente Update(Cliente cliente, Long id) {
        Cliente o = this.Read(id);
        
        Cliente newCliente = new Cliente(id, null, null, null);
        return null;
         
    }

    @Override
    public void Delete(Long id) {
       
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }
    
}
