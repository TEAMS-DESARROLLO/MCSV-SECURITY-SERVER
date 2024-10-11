package com.iat.security.service.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.iat.security.commons.Filter;
import com.iat.security.commons.IPaginationCommons;
import com.iat.security.commons.PaginationModel;
import com.iat.security.commons.SortModel;
import com.iat.security.dto.UserListResponseDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioPaginationService implements IPaginationCommons<UserListResponseDto>{
    
    private final  EntityManager entityManager;

    @Override
	public Page<UserListResponseDto> pagination(PaginationModel pagination) {
		try {

            String sqlCount = "SELECT count(u) " + getFrom().toString() + getFilters(pagination.getFilters()).toString();
			String sqlSelect = getSelect().toString() + getFrom().toString() + getFilters(pagination.getFilters()).toString() + getOrder(pagination.getSorts());

			Query queryCount = entityManager.createQuery(sqlCount);
			Query querySelect = entityManager.createQuery(sqlSelect);

			this.setParams(pagination.getFilters(), queryCount);
			this.setParams(pagination.getFilters(), querySelect);

			querySelect.setFirstResult((pagination.getPageNumber()) * pagination.getRowsPerPage());
			querySelect.setMaxResults(pagination.getRowsPerPage());

            Long total = (long) queryCount.getSingleResult();

            querySelect.setFirstResult((pagination.getPageNumber()) * pagination.getRowsPerPage());
			querySelect.setMaxResults(pagination.getRowsPerPage());

			@SuppressWarnings("unchecked")
			List<UserListResponseDto> lista = querySelect.getResultList();            
            
			PageRequest pageable = PageRequest.of(pagination.getPageNumber(), pagination.getRowsPerPage());

			return new PageImpl<>(lista,pageable, total);

		} catch (RuntimeException e) {
			throw new ServiceException("error when generating the pagination " + e.getMessage(), e );
		}
	}

    @Override
	public StringBuilder getSelect() {
		return new StringBuilder("SELECT new com.iat.security.dto.UserListResponseDto(u.idUsuario,u.username,u.nombres,u.registrationStatus,u.statusUser,u.expirationDate ) ");
	}

	@Override
	public StringBuilder getFrom() {
		return new StringBuilder(" FROM Usuario u ");
	}

	@Override
	public StringBuilder getFilters(List<Filter> filters) {
		 StringBuilder sql = new StringBuilder("where 1=1 AND u.registrationStatus  = 'A' ");

         for(Filter filtro:filters){            
            if(filtro.getField().equals("username")){
                sql.append(" AND UPPER(u.username) LIKE UPPER(:username)");
            }
            if(filtro.getField().equals("nombres")){
                sql.append(" AND UPPER(u.nombres) LIKE UPPER(:nombres) ");
            }
        }   
        return sql;
	}

	@Override
	public Query setParams(List<Filter> filters, Query query) {
        for(Filter filtro:filters){            
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
        if(!sorts.isEmpty()){
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

                if(sort.getColName().equals("expirationDate")){
                    if(flagMore)
                        sql.append(", ");
                    sql.append( " expirationDate " + sort.getSort() );
                    flagMore = true;
                }
                
                if(sort.getColName().equals("registrationStatus")){
                    if(flagMore)
                        sql.append(", ");
                    sql.append( " registrationStatus " + sort.getSort() );
                    flagMore = true;
                }
           }
        }
         return sql;
	
	}

}
