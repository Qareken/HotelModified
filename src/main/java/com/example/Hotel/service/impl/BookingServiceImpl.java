package com.example.Hotel.service.impl;

import com.example.Hotel.dto.bookingDto.BookingRequestDto;
import com.example.Hotel.dto.bookingDto.BookingResponseDto;
import com.example.Hotel.entity.Booking;
import com.example.Hotel.entity.UnAvailableDates;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.exception.EntityNotFoundException;
import com.example.Hotel.mapper.BookingMapper;
import com.example.Hotel.repository.BookingRepository;
import com.example.Hotel.repository.RoomRepository;
import com.example.Hotel.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Set;
@Service
public class BookingServiceImpl extends CommonServiceImpl<BookingRequestDto, BookingResponseDto, Booking, Long> {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    private final KafkaProducerService kafkaProducerService;
    public BookingServiceImpl(BookingRepository repository, BookingMapper mapper, UserRepository userService, RoomRepository roomService, KafkaProducerService kafkaProducerService) {
        super(repository, mapper);
        this.roomRepository = roomService;
        this.userRepository = userService;

        this.kafkaProducerService = kafkaProducerService;
    }
    @Override
    public BookingResponseDto save(BookingRequestDto bookingRequestDto) {
        var booking = mapper.toEntity(bookingRequestDto);
        var roomId = booking.getRoom().getId();
        var userId= booking.getUser().getId();
        var room = roomRepository.findById(booking.getRoom().getId()).orElseThrow(()->new EntityNotFoundException(MessageFormat.format("Room with this {} : id not found", roomId)));
        var user = userRepository.findById(booking.getUser().getId()).orElseThrow(()->new EntityNotFoundException(MessageFormat.format("User with this {} : id not found", userId)));
        if (validBookingTime(room.getUnavailableDates(), booking.getArrivalDate(), booking.getDepartureDate())) {
            throw new BadRequestException(MessageFormat.format("Room is not available between {0} and {1} period", booking.getArrivalDate(), booking.getDepartureDate()));
        }
        UnAvailableDates dates = UnAvailableDates.builder()
                .startDate(booking.getArrivalDate())
                .endDate(booking.getDepartureDate())
                .room(room).build();
        var listUnavailable = room.getUnavailableDates();
        listUnavailable.add(dates);
        room.setUnavailableDates(listUnavailable);
        room = roomRepository.save(room);
        booking.setUser(user);
        booking.setRoom(room);
        booking=repository.save(booking);
        kafkaProducerService.sendBookingEvent(booking);
        return mapper.toResponseDto(booking);
    }
    private boolean validBookingTime(Set<UnAvailableDates> unAvailableDates, LocalDateTime arrival, LocalDateTime departure) {
        return unAvailableDates.stream().anyMatch(unAvailable -> unAvailable.getStartDate().isBefore(departure) && arrival.isBefore(unAvailable.getEndDate()));
    }

}
