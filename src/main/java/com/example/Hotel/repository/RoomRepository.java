package com.example.Hotel.repository;

import com.example.Hotel.entity.Room;
import com.example.Hotel.entity.Users;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> , CommonRepository<Room, Long>{
    @EntityGraph(attributePaths = {"unavailableDates"})
    @NotNull
    Optional<Room> findById(@NotNull Long id);
}
