package com.iat.security.commons;

import java.util.List;

import org.springframework.data.domain.Page;

import jakarta.persistence.Query;

public interface IPaginationCommons <T>{
    public Page<T> pagination(PaginationModel pagination);

    public StringBuilder getSelect();
    public StringBuilder getFrom();
    public StringBuilder getFilters(List<Filter> filters);
    public Query setParams(List<Filter> filters, Query query);
    public StringBuilder getOrder(List<SortModel> sorts);
}
