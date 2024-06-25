package com.example.Hotel.service.impl;


import com.example.Hotel.entity.Users;
import com.example.Hotel.entity.event.BookingEvent;
import com.example.Hotel.entity.event.RegistrationEvent;
import com.example.Hotel.repository.BookingEventRepository;
import com.example.Hotel.repository.RegistrationEventRepository;
import com.example.Hotel.service.ListenerEventService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
@Slf4j
public class ListenerEventServiceImpl implements ListenerEventService {
    private final BookingEventRepository bookingEventRepository;

    private final RegistrationEventRepository registrationEventRepository;

    @KafkaListener(topics = "${app.kafka.userRegistrationTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaConcurrentKafkaListenerContainerFactory")
    @Override
    public void userRegistrationEvent(RegistrationEvent registrationEvent) {
        log.info("registrationEvent userId {}", registrationEvent.getUserId());
        var event =registrationEventRepository.save(registrationEvent);
        log.info("registration Event successfully loaded");
        log.info("user event id {} and userID {} ", event.getId(), event.getUserId());


    }
    @KafkaListener(topics = "${app.kafka.bookingRegistrationTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaConcurrentKafkaListenerContainerFactory")
    @Override
    public void bookingEvent(BookingEvent bookingEvent) {
        bookingEventRepository.save(bookingEvent);
        log.info("booking Event successfully loaded");
    }

}
