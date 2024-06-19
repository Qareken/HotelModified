package com.example.Hotel.repository;

import com.example.Hotel.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> , CommonRepository<City, Long> {
    Optional<City> findCityByName(String name);

}
