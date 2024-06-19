package com.example.Hotel.mapper;

import com.example.Hotel.dto.roomDto.RoomRequestDto;
import com.example.Hotel.dto.roomDto.RoomResponseDto;
import com.example.Hotel.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {HotelMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper extends CommonMapper<Room, RoomRequestDto, RoomResponseDto> {
    @Mapping(source = "hotelId", target = "hotel.id")
    Room toEntity(RoomRequestDto roomRequestDTO);

    @Mapping(source = "hotel.id", target = "hotelId")
    RoomRequestDto toDto(Room room);




}
