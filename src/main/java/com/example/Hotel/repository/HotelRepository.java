package com.example.Hotel.repository;

import com.example.Hotel.entity.City;
import com.example.Hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> , CommonRepository<Hotel, Long>{
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Hotel h WHERE h.name = :name AND h.city = :city")
    boolean existsHotelByNameAndCity(@Param("name") String name, @Param("city") City city);

}
