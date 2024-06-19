package com.example.Hotel.repository;

import com.example.Hotel.entity.Role;
import com.example.Hotel.entity.enumurated.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> , CommonRepository<Role, Long>{

    Role findByAuthority(RoleType authority);
}
