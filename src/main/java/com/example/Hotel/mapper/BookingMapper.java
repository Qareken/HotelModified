package com.example.Hotel.mapper;

import com.example.Hotel.dto.bookingDto.BookingRequestDto;
import com.example.Hotel.dto.bookingDto.BookingResponseDto;
import com.example.Hotel.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {RoomMapper.class, UserMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper extends CommonMapper<Booking, BookingRequestDto, BookingResponseDto> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "room.id", target = "roomId")
    BookingRequestDto toDto(Booking booking);
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "roomId", target = "room.id")
    Booking toEntity( BookingRequestDto bookingRequestDto);

    @Mapping(source = "user", target = "userResponseDto")
    @Mapping(source = "room", target = "roomResponseDto")
    BookingResponseDto toResponseDto(Booking booking);
}
