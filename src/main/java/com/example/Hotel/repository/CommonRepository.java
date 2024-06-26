package com.example.Hotel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository <ENTITY, ID>extends JpaRepository<ENTITY, ID> , JpaSpecificationExecutor<ENTITY> {
}
