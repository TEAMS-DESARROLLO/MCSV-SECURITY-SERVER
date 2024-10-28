package com.iat.security.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.iat.security.commons.Filter;
import com.iat.security.commons.IPaginationCommons;
import com.iat.security.commons.PaginationModel;
import com.iat.security.commons.SortModel;
import com.iat.security.dto.RolResponseDto;
import com.iat.security.exception.ServiceException;
import com.iat.security.util.DateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class IRolPaginationService implements IPaginationCommons<RolResponseDto>{

	private final EntityManager entityManager;
    
    @Override
    public Page<RolResponseDto> pagination(PaginationModel pagination) {
       try {

			String sqlCount = "SELECT count(r) " + getFrom().toString() + getFilters(pagination.getFilters()).toString();
			String sqlSelect = getSelect().toString() + getFrom().toString() + getFilters(pagination.getFilters()).toString() + getOrder(pagination.getSorts());
            
			Query queryCount = entityManager.createQuery(sqlCount);
            
			Query querySelect = entityManager.createQuery(sqlSelect);
            this.setParams(pagination.getFilters(), queryCount);
            this.setParams(pagination.getFilters(), querySelect);
            Long total = (long) queryCount.getSingleResult();
            querySelect.setFirstResult((pagination.getPageNumber()) * pagination.getRowsPerPage());
            querySelect.setMaxResults(pagination.getRowsPerPage());
            @SuppressWarnings("unchecked")
			List<RolResponseDto> lista = querySelect.getResultList();
            PageRequest pageable = PageRequest.of(pagination.getPageNumber(), pagination.getRowsPerPage());
            Page<RolResponseDto> page = new PageImpl<RolResponseDto>(lista, pageable, total);
            return page;
		} catch (RuntimeException e) {
			throw new ServiceException("error when generating the pagination " , e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @Override
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT new com.iat.security.dto.RolResponseDto(r.id, r.name, r.description, r.createdAt AS createdAt, r.updatedAt AS updatedAt, r.registrationStatus as registrationStatus) ");
        return sql;
    }

    @Override
    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM Rol r ");
        return sql;
    }

    @Override
    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");
        
        for(Filter filtro:filters){
            if(filtro.getField().equals("id")){
                sql.append(" AND r.id = :id");
            }
            if(filtro.getField().equals("name")){
                sql.append(" AND r.name LIKE :name ");
            }
            if(filtro.getField().equals("description")){
                sql.append(" AND r.description LIKE :description ");
            }
            if(filtro.getField().equals("createdAt")){
            	sql.append(" AND DATE(r.createdAt) = :createdAt");
            }
            if(filtro.getField().equals("updatedAt")){
            	sql.append(" AND DATE(r.updatedAt) = :updatedAt");
            }
            if(filtro.getField().equals("registrationStatus")){
            	sql.append(" r.registrationStatus = :registrationStatus");
            }
        }
        
        return sql;
    }

    @Override
    public Query setParams(List<Filter> filters, Query query) {
        for(Filter filtro:filters){
            if(filtro.getField().equals("id")){
                query.setParameter("id",filtro.getValue() );
            }
            if(filtro.getField().equals("name")){
                query.setParameter("name","%"+filtro.getValue()+"%");
            }
            if(filtro.getField().equals("description")){
                query.setParameter("description","%"+filtro.getValue()+"%");
            }
            if(filtro.getField().equals("createdAt")){
	           	LocalDate createdAt = DateUtil.convertStringToLocalDate(filtro.getValue().trim());
	           	query.setParameter("createdAt", createdAt);
	        }
	        if(filtro.getField().equals("updatedAt")){
	           	LocalDate updatedAt = DateUtil.convertStringToLocalDate(filtro.getValue().trim());
	           	query.setParameter("updatedAt", updatedAt);
	        }
            if(filtro.getField().equals("registrationStatus")){
                LocalDate registrationStatus = DateUtil.convertStringToLocalDate(filtro.getValue().trim());
                query.setParameter("registrationStatus", registrationStatus);
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
                if(sort.getColName().equals("id")){
                    if(flagMore)
                        sql.append(", ");

                    sql.append( " id " + sort.getSort() );
                    flagMore = true;
                }

                if(sort.getColName().equals("name")){
                    if(flagMore)
                        sql.append(", ");
                    sql.append( " name " + sort.getSort() );
                    flagMore = true;
                }

                if(sort.getColName().equals("description")){
                    if(flagMore)
                        sql.append(", ");
                    sql.append( " description " + sort.getSort() );
                    flagMore = true;
                }
                if(sort.getColName().equals("createdAt")){
                    if(flagMore)
                        sql.append(", ");
                    sql.append( " createdAt " + sort.getSort() );
                    flagMore = true;
                }
                if(sort.getColName().equals("updatedAt")){
                    if(flagMore)
                        sql.append(", ");
                    sql.append( " updatedAt " + sort.getSort() );
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
