package com.example.Hotel.service.impl;

import com.example.Hotel.entity.Booking;
import com.example.Hotel.entity.event.BookingEvent;
import com.example.Hotel.entity.event.RegistrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String , Object> kafkaTemplate;
    @Value("${app.kafka.userRegistrationTopic}")
    private String registrationEventTopic ;
    @Value("${app.kafka.bookingRegistrationTopic}")
    private String bookingEventTopic ;
    public void sendBookingEvent(Booking booking){
        kafkaTemplate.send(bookingEventTopic, BookingEvent.builder().userId(booking.getUser().getId())
                .roomId(booking.getRoom().getId())
                .arrivalTime(booking.getArrivalDate())
                .departureTime(booking.getDepartureDate())
                .userId(booking.getUser().getId())
                .createdTime(LocalDateTime.now()));
    }
    public void sendUserRegistrationEvent(Long id){
        kafkaTemplate.send(registrationEventTopic, RegistrationEvent.builder().userId(id).createdTime(LocalDateTime.now()).build());
    }
}
