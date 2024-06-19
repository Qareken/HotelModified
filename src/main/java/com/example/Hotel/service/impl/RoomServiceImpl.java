package com.example.Hotel.service.impl;


import com.example.Hotel.dto.RoomRequestDto;
import com.example.Hotel.dto.RoomResponseDto;
import com.example.Hotel.entity.Room;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.mapper.CommonMapper;
import com.example.Hotel.repository.CommonRepository;
import com.example.Hotel.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class RoomServiceImpl extends CommonServiceImpl<RoomRequestDto, RoomResponseDto, Room, Long> {
    private final HotelRepository hotelRepository;
    public RoomServiceImpl(CommonRepository<Room, Long> repository, CommonMapper<Room, RoomRequestDto, RoomResponseDto> mapper, HotelRepository hotelRepository) {
        super(repository, mapper);
        this.hotelRepository = hotelRepository;
    }
    @Override
    public RoomResponseDto save(RoomRequestDto roomRequestDto) {
        var room = mapper.toEntity(roomRequestDto);
        var hotel = hotelRepository.findById(roomRequestDto.getHotelId());
        if(hotel.isPresent()){
            room.setHotel(hotel.get());
            return mapper.toResponseDto(repository.save(room));
        }else {
            throw new BadRequestException(MessageFormat.format("Hotel with this id {} has not been registered", roomRequestDto.getHotelId()));
        }
    }

}
