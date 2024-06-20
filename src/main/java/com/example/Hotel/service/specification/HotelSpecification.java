package com.example.Hotel.service.specification;

import com.example.Hotel.dto.filter.HotelSearch;
import com.example.Hotel.entity.Hotel;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HotelSpecification extends CommonSpecification<Hotel> {
    public static Specification<Hotel> getHotels(HotelSearch search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            addEqualPredicateIfNotNull(criteriaBuilder, root.get("id"), search.getId(), predicates);
            addLikePredicateIfNotNull(criteriaBuilder, root.get("name"), search.getName(), predicates);
            addLikePredicateIfNotNull(criteriaBuilder, root.get("title"), search.getTitle(), predicates);
            addLikePredicateIfNotNull(criteriaBuilder, root.get("city").get("name"), search.getCityName(), predicates);
            addLikePredicateIfNotNull(criteriaBuilder, root.get("address"), search.getAddress(), predicates);
            if(search.getDistance()!=0){
                addEqualPredicateIfNotNull(criteriaBuilder, root.get("distance"), search.getDistance(), predicates);
            }
            addEqualPredicateIfNotNull(criteriaBuilder, root.get("rating"), search.getRating(), predicates);
            addEqualPredicateIfNotNull(criteriaBuilder, root.get("numberOfRatings"), search.getRatingCount(), predicates);
            var spec = criteriaBuilder.and(predicates.toArray(new Predicate[0]));



                log.info("Generated specification predicates: {}", spec);

                return spec;
            };


    }
}
