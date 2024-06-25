package com.example.Hotel.repository;

import com.example.Hotel.entity.event.BookingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingEventRepository extends MongoRepository<BookingEvent, String> {
}
