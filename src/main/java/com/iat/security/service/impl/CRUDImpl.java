package com.iat.security.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.iat.security.commons.Filter;
import com.iat.security.commons.IBaseInterfaceService;
import com.iat.security.commons.SortModel;
import com.iat.security.exception.ModelNotFoundException;
import com.iat.security.exception.RepositoryException;
import com.iat.security.repository.IGenericRepository;


public abstract class CRUDImpl<T,ID> implements IBaseInterfaceService<T,ID>  {

    protected abstract IGenericRepository<T,ID> getRepositorio();

    @Override
    public Long count() {
        return getRepositorio().count();
    }

    @Override
    public T create(T entidad) {

        return getRepositorio().save(entidad);
    }


    @Override
    public List<T> createAll(List<T> entidades) {
        return  getRepositorio().saveAll(entidades);
    }

    @Override
    public Optional<T> entityById(ID id) {

        Optional<T> t = getRepositorio().findById(id);

        if(t.isPresent()){
            return Optional.of(getRepositorio().findById(id).get());
        }

         return Optional.ofNullable(null);
  }

    @Override
    public void delete(T entidad) {
        getRepositorio().delete(entidad);
        
    }

    

    @Override
    public void deleteById(ID id) {
        getRepositorio().findById(id).orElseThrow(()->new ModelNotFoundException("ID NOT FOUND " + id )) ;
        try {
            getRepositorio().deleteById(id) ;
            
        } catch (Exception e) {
            throw new RepositoryException("ERROR WHILE DELETING, CHECK IF THERE ARE FOREIGN KEYS RELATED TO THE ROW");
        }
        
    }

    @Override
    public Boolean exists(ID id) {
        return getRepositorio().existsById(id) ;
    }

    @Override
    public List<T> getAll() {
        return getRepositorio().findAll();
    }

    @Override
    public T update(T entidad, ID id) {
        return getRepositorio().save(entidad);
    }

    @Override
    public Page<?> pagination(Integer pagenumber, Integer rows, List<SortModel> sortModel, Filter filter) {
        return null;
    }
    
}
