package com.iat.security.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.iat.security.commons.Filter;
import com.iat.security.commons.IPaginationCommons;
import com.iat.security.commons.PaginationModel;
import com.iat.security.commons.SortModel;
import com.iat.security.dto.UserResponseDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioPaginationService implements IPaginationCommons<UserResponseDto>{

    private final  EntityManager entityManager;

    @Override
    public Page<UserResponseDto> pagination(PaginationModel pagination) {
        String sqlCount  = "SELECT count(a) " + getFrom().toString() + getFilters(pagination.getFilters()).toString();
        String sqlSelect = getSelect().toString() + getFrom().toString() +getFilters( pagination.getFilters()).toString() + getOrder(pagination.getSorts());
            
        Query queryCount = entityManager. createQuery(sqlCount);
        Query querySelect = entityManager.createQuery(sqlSelect);

        this.setParams(pagination.getFilters(), queryCount);
        this.setParams(pagination.getFilters(), querySelect);

        Long total = (long) queryCount.getSingleResult();

        querySelect.setFirstResult((pagination.getPageNumber()) * pagination.getRowsPerPage());
        querySelect.setMaxResults(pagination.getRowsPerPage());        

        @SuppressWarnings("unchecked")
        List<UserResponseDto> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(pagination.getPageNumber(), pagination.getRowsPerPage());

        Page<UserResponseDto> page = new PageImpl<UserResponseDto>(lista, pageable, total);
        return page;
    }

    @Override
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT new com.iat.security.dto.UserResponseDto(a.idUsuario,a.username,a.nombres,a.registrationStatus) ");
        return sql;
    }

    @Override
    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM Usuario a ");
        return sql;
    }

    @Override
    public StringBuilder getFilters(List<Filter> filters) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder("where 1=1 ");
        return sql;
    }

    @Override
    public Query setParams(List<Filter> filters, Query query) {

        for(Filter filtro:filters){
            if(filtro.getField().equals("idUsuario")){
                query.setParameter("idUsuario",filtro.getValue() );
            }
            if(filtro.getField().equals("username")){
                query.setParameter("username","%"+filtro.getValue()+"%");
            }
            if(filtro.getField().equals("nombres")){
                query.setParameter("nombres","%"+filtro.getValue()+"%");
            }
        }
        return query;
    }

    @Override
    public StringBuilder getOrder(List<SortModel> sorts) {
        boolean flagMore = false;
        StringBuilder sql = new StringBuilder("");
        if(sorts.size() > 0){
            sql.append(" ORDER BY ");

            for(SortModel sort:sorts){
                if(sort.getColName().equals("idUsuario")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " idUsuario " + sort.getSort() );
                    flagMore = true;
                }


                if(sort.getColName().equals("nombres")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " nombres " + sort.getSort() );
                    flagMore = true;
                }
                if(sort.getColName().equals("username")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " username " + sort.getSort() );
                    flagMore = true;
                }
            }
        }
        return sql;
    }

}
