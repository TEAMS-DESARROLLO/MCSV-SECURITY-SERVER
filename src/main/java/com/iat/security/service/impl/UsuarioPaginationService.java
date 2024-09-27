package com.iat.security.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        String sqlCount  = "SELECT COUNT(DISTINCT a.id_usuario) " + getFrom().toString() + getFilters(pagination.getFilters()).toString();
        String sqlSelect = getSelect()
                            .append (getFrom() )
                            .append( getFilters( pagination.getFilters()) )
                            .append( getOrder(pagination.getSorts())).toString();
        Query queryCount = entityManager. createNativeQuery(sqlCount);
        Query querySelect = entityManager.createNativeQuery(sqlSelect);

        this.setParams(pagination.getFilters(), queryCount);
        this.setParams(pagination.getFilters(), querySelect);

        Long total = (long) queryCount.getSingleResult();

        querySelect.setFirstResult((pagination.getPageNumber()) * pagination.getRowsPerPage());
        querySelect.setMaxResults(pagination.getRowsPerPage());        

        @SuppressWarnings("unchecked")
        List<Object[]> results = querySelect.getResultList();

        List<UserResponseDto> lista = results.stream()
            .map(result -> {
                Long idUsuario = ((Number) result[0]).longValue();
                String username = (String) result[1];
                String nombres = (String) result[2];
                String registrationStatus = (String) result[3];
        
                List<Long> roles = Arrays.stream(((String) result[4]).split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

                return new UserResponseDto(idUsuario, username, nombres, registrationStatus, roles);
            })
            .collect(Collectors.toList());

        PageRequest pageable = PageRequest.of(pagination.getPageNumber(), pagination.getRowsPerPage());

        Page<UserResponseDto> page = new PageImpl<UserResponseDto>(lista, pageable, total);
        return page;
    }

    @Override
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder(" SELECT a.id_usuario ,a.username,a.nombres,a.registration_status ,STRING_AGG( CAST(ur.id_rol AS VARCHAR) , ',' ORDER BY ur.id_rol) AS roles ");
        return sql;
    }

    @Override
    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM x_usuario a INNER JOIN x_usuario_rol ur ON a.id_usuario = ur.id_usuario ");
        return sql;
    }

    @Override
    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder(" where 1=1 ");

        for(Filter filtro:filters){
            if(filtro.getField().equals("idUsuario")){
                sql.append(" AND a.id_usuario = :idUsuario");
            }
            if(filtro.getField().equals("username")){
                sql.append(" AND UPPER(a.username) LIKE UPPER(:username)");
            }
            if(filtro.getField().equals("nombres")){
                sql.append(" AND UPPER(a.nombres) LIKE UPPER(:nombres) ");
            }
            if(filtro.getField().equals("registrationStatus")){
                sql.append(" AND UPPER(a.registration_status) LIKE UPPER(:registrationStatus) ");
            }

        }

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
            if(filtro.getField().equals("registrationStatus")){
                query.setParameter("registrationStatus",filtro.getValue());
            }
        }
        return query;
    }

    @Override
    public StringBuilder getOrder(List<SortModel> sorts) {
        StringBuilder sql = new StringBuilder("");
        sql.append("""
                 GROUP BY a.id_usuario , a.username , a.nombres , a.registration_status    
                 ORDER BY a.id_usuario  asc 
                """);
        return sql;
    }


}
