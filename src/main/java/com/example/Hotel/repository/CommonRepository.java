package com.example.Hotel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommonRepository <ENTITY, ID>extends JpaRepository<ENTITY, ID> , JpaSpecificationExecutor<ENTITY> {
}
