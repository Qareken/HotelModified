package com.example.Hotel.service.impl;

import com.example.Hotel.dto.BookingRequestDto;
import com.example.Hotel.dto.BookingResponseDto;
import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.entity.UnAvailableDates;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.mapper.BookingMapper;
import com.example.Hotel.mapper.PageMapper;
import com.example.Hotel.repository.BookingRepository;
import com.example.Hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserServiceImpl userService;
    private final RoomServiceImpl roomService;
    private final BookingMapper bookingMapper;
    private final PageMapper pageMapper;
    @Override
    public BookingResponseDto save(BookingRequestDto bookingRequestDto) {
        var booking = bookingMapper.toEntity(bookingRequestDto);
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
        return bookingMapper.toResponseDto(bookingRepository.save(booking));
    }

    private boolean validBookingTime(Set<UnAvailableDates> unAvailableDates, LocalDateTime arrival, LocalDateTime departure) {
        return unAvailableDates.stream().anyMatch(unAvailable -> unAvailable.getStartDate().isBefore(departure) && arrival.isBefore(unAvailable.getEndDate()));
    }

    @Override
    public PageResponseDto<BookingResponseDto> findAll(Pageable pageable) {
        return pageMapper.toPageResponseDto(bookingRepository.findAll(pageable).map(bookingMapper::toResponseDto));
    }
}
