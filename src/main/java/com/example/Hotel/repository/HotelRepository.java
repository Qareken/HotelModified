package com.example.Hotel.repository;

import com.example.Hotel.entity.City;
import com.example.Hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    boolean existsHotelByNameAndCity(String name, City city);
}
