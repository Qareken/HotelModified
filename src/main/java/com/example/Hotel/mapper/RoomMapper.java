package com.example.Hotel.mapper;

import com.example.Hotel.dto.HotelResponseDto;
import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.dto.RoomRequestDto;
import com.example.Hotel.dto.RoomResponseDto;
import com.example.Hotel.entity.Hotel;
import com.example.Hotel.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",uses = {HotelMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {
    @Mapping(source = "hotelId", target = "hotel.id")
    Room toEntity(RoomRequestDto roomRequestDTO);

    @Mapping(source = "hotel.id", target = "hotelId")
    RoomRequestDto toRequestDTO(Room room);

    @Mapping(source = "hotel", target = "hotel")
    RoomResponseDto toResponseDTO(Room room);
    List<RoomResponseDto> toRoomResponseDtoList(List<Room> hotelList);

}
