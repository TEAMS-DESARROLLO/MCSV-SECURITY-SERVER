package com.iat.security.service;

import com.iat.security.model.Cliente;

public interface IClienteService {
    

    Cliente Create(Cliente cliente);
    Cliente Read(Long id);
    Cliente Update(Cliente cliente, Long id);
    void Delete(Long id);
    

}
