package com.example.Hotel.service.specification;

import com.example.Hotel.dto.filter.HotelSearch;
import com.example.Hotel.dto.filter.RoomSearch;
import com.example.Hotel.entity.Hotel;
import com.example.Hotel.entity.Room;
import com.example.Hotel.entity.UnAvailableDates;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class RoomSpecification extends CommonSpecification<Room> {
    public static Specification<Room> getRoom(RoomSearch search) {
        return (root, query, criteriaBuilder) -> {
            log.info("search objects {}", search);
            List<Predicate> predicates = new ArrayList<>();
            addEqualPredicateIfNotNull(criteriaBuilder, root.get("id"), search.getId(), predicates);
            addLikePredicateIfNotNull(criteriaBuilder, root.get("title"), search.getTitle(), predicates);

            if (search.getMinPrice() != null || search.getMaxPrice() != null) {
                predicates.add(hasPriceBetween(search.getMinPrice(), search.getMaxPrice()).toPredicate(root, query, criteriaBuilder));
            }
            if(search.getMaxOccupancy()!=0){
                addEqualPredicateIfNotNull(criteriaBuilder, root.get("maxOccupancy"), search.getMaxOccupancy(), predicates);
            }
            addEqualPredicateIfNotNull(criteriaBuilder, root.get("hotel").get("id"), search.getHotelId(), predicates);
            log.info("to check available");

            if (search.getArrivalDate() != null && search.getDepartureDate()!=null) {
                log.info("objects are not empty");
                predicates.add(isAvailableBetween(search.getArrivalDate(), search.getDepartureDate()).toPredicate(root, query, criteriaBuilder));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Room> hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return criteriaBuilder.conjunction();
            } else if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        };
    }

    private static Specification<Room> isAvailableBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null) {
                return criteriaBuilder.conjunction();
            }
            log.info("startDate {} ", startDate);
            log.info("endDate {} ", endDate);
            Join<Room, UnAvailableDates> join = root.join("unavailableDates", JoinType.LEFT);

            Predicate noOverlap = criteriaBuilder.or(
                    criteriaBuilder.isNull(join.get("id")),
                    criteriaBuilder.or(
                            criteriaBuilder.lessThanOrEqualTo(join.get("endDate"), startDate),
                            criteriaBuilder.greaterThanOrEqualTo(join.get("startDate"), endDate)
                    )
            );
            log.info("method is called");

            query.distinct(true); // avoid from duplicate room
            return noOverlap;
        };
    }
}
