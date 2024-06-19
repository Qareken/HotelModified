package com.example.Hotel.repository;

import com.example.Hotel.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>, CommonRepository<Users, Long> {

    Boolean existsByNameOrEmail(String username, String email);
    @EntityGraph(attributePaths = {"roles"})

    Optional<Users> findUsersByName(String name);

}
