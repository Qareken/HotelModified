package com.example.Hotel.configuration;

import com.example.Hotel.entity.Hotel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

@Component
public class HotelSpecificationExecutor {

    @PersistenceContext
    private EntityManager entityManager;

    public HotelSpecificationExecutor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public void executeSpecification(Specification<Hotel> spec) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hotel> query = builder.createQuery(Hotel.class);
        query.where(spec.toPredicate(query.from(Hotel.class), query, builder));

        TypedQuery<Hotel> typedQuery = entityManager.createQuery(query);
        String sqlQuery = typedQuery.unwrap(org.hibernate.query.Query.class).getQueryString();
        System.out.println("Generated SQL Query: " + sqlQuery);
    }
}
