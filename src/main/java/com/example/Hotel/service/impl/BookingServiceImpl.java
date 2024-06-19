package com.example.Hotel.service.impl;

import com.example.Hotel.dto.BookingRequestDto;
import com.example.Hotel.dto.BookingResponseDto;
import com.example.Hotel.entity.Booking;
import com.example.Hotel.entity.UnAvailableDates;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.mapper.BookingMapper;
import com.example.Hotel.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Set;

@Service

public class BookingServiceImpl extends CommonServiceImpl<BookingRequestDto, BookingResponseDto, Booking, Long> {

    private final UserServiceImpl userService;
    private final RoomServiceImpl roomService;

    public BookingServiceImpl(BookingRepository repository, BookingMapper mapper, UserServiceImpl userService, RoomServiceImpl roomService) {
        super(repository, mapper);
        this.roomService = roomService;
        this.userService = userService;
    }

    @Override
    public BookingResponseDto save(BookingRequestDto bookingRequestDto) {
        var booking = mapper.toEntity(bookingRequestDto);
        var room = roomService.findRoomById(booking.getRoom().getId());
        var user = userService.findUsersById(booking.getUser().getId());
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
        room = roomService.saveRoom(room);
        booking.setUser(user);
        booking.setRoom(room);
        return mapper.toResponseDto(repository.save(booking));
    }

    private boolean validBookingTime(Set<UnAvailableDates> unAvailableDates, LocalDateTime arrival, LocalDateTime departure) {
        return unAvailableDates.stream().anyMatch(unAvailable -> unAvailable.getStartDate().isBefore(departure) && arrival.isBefore(unAvailable.getEndDate()));
    }

}
