package com.iat.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
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
		try {

			String sqlSelect = getSelect().toString() + getFrom().toString() + getFilters(pagination.getFilters()).toString() + getOrder(pagination.getSorts());

			Query querySelect = entityManager.createQuery(sqlSelect);

			this.setParams(pagination.getFilters(), querySelect);

			querySelect.setFirstResult((pagination.getPageNumber()) * pagination.getRowsPerPage());
			querySelect.setMaxResults(pagination.getRowsPerPage());

			@SuppressWarnings("unchecked")
			List<UserResponseDto> lista = querySelect.getResultList();            
            List<UserResponseDto> usuarios = lista.stream()
                                                        .collect(Collectors.groupingBy(UserResponseDto::getIdUsuario))
                                                        .values().stream()
                                                        .map(grupo -> {
                                                            if (grupo.isEmpty()) {
                                                                return null;
                                                            }
                                                            UserResponseDto primerUsuario = grupo.get(0);                                                            
                                                            Set<Long> roles = grupo.stream()
                                                                .map(UserResponseDto::getIdRol)
                                                                .filter(Objects::nonNull)
                                                                .collect(Collectors.toSet());
                                                            
                                                            primerUsuario.setRoles(new ArrayList<>(roles));
                                                            return primerUsuario;
                                                        })
                                                        .filter(Objects::nonNull)
                                                        .collect(Collectors.toList());

            Long total =  Long.valueOf(usuarios.size());
			PageRequest pageable = PageRequest.of(pagination.getPageNumber(), pagination.getRowsPerPage());

			return new PageImpl<>(usuarios,pageable, total);
		} catch (RuntimeException e) {
			throw new ServiceException("error when generating the pagination " + e.getMessage(), e );
		}
	}

    @Override
	public StringBuilder getSelect() {
		return new StringBuilder("SELECT new com.iat.security.dto.UserResponseDto(xu.idUsuario,xu.username,xu.nombres,xu.registrationStatus,xu.statusUser,xu.expirationDate,xur.rol.id ) ");
	}

	@Override
	public StringBuilder getFrom() {
		return new StringBuilder(" FROM UsuarioRol xur  ")
                         .append(" INNER JOIN Usuario xu ON xur.usuario.idUsuario = xu.idUsuario ")
                         .append(" INNER JOIN Rol r ON xur.rol.id = r.id  ");
	}

	@Override
	public StringBuilder getFilters(List<Filter> filters) {
		 StringBuilder sql = new StringBuilder("where 1=1 AND xu.registrationStatus  = 'A' ");

         for(Filter filtro:filters){            
            if(filtro.getField().equals("username")){
                sql.append(" AND UPPER(xu.username) LIKE UPPER(:username)");
            }
            if(filtro.getField().equals("nombres")){
                sql.append(" AND UPPER(xu.nombres) LIKE UPPER(:nombres) ");
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
        StringBuilder sql = new StringBuilder("");
        sql.append("""
                 GROUP BY xu.idUsuario , xu.username , xur.rol.id
                 ORDER BY xu.idUsuario  asc 
                """);
        return sql;
	}

}
