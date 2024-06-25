package com.example.Hotel.service;

import com.example.Hotel.entity.Booking;
import com.example.Hotel.entity.Users;

import com.example.Hotel.entity.event.BookingEvent;
import com.example.Hotel.entity.event.RegistrationEvent;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.UUID;

public interface ListenerEventService {

    void userRegistrationEvent(@Payload RegistrationEvent registrationEvent);

    void bookingEvent(@Payload BookingEvent bookingEvent);
}
