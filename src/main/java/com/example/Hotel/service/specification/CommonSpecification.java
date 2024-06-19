package com.example.Hotel.service.specification;


import com.example.Hotel.dto.PageRequestDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public abstract class CommonSpecification<T> {
    public Pageable createPagination(PageRequestDto pageRequestDto) {
        return PageRequest.of(
                pageRequestDto.getPage(),
                pageRequestDto.getSize(),
                Sort.Direction.fromString(pageRequestDto.getSortDirection().toUpperCase()),
                pageRequestDto.getSortBy()
        );
    }

    protected static Predicate addLikePredicateIfNotNull(CriteriaBuilder criteriaBuilder, Path<String> path, String value, List<Predicate> predicates) {
        if (value != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + value.toLowerCase() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    protected static Predicate addEqualPredicateIfNotNull(CriteriaBuilder criteriaBuilder, Path<Object> path, Object value, List<Predicate> predicates) {
        if (value != null) {
            predicates.add(criteriaBuilder.equal(path, value));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}