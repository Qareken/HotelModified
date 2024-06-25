package com.example.Hotel.repository;

import com.example.Hotel.entity.event.RegistrationEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationEventRepository extends MongoRepository<RegistrationEvent, String> {

}
