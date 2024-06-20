package com.example.Hotel.service.impl;


import com.example.Hotel.dto.filter.CommonSearch;
import com.example.Hotel.dto.filter.PageRequestDto;
import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.dto.filter.RoomSearch;
import com.example.Hotel.dto.roomDto.RoomRequestDto;
import com.example.Hotel.dto.roomDto.RoomResponseDto;
import com.example.Hotel.entity.Room;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.mapper.CommonMapper;
import com.example.Hotel.repository.CommonRepository;
import com.example.Hotel.repository.HotelRepository;
import com.example.Hotel.service.specification.RoomSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@Slf4j
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

    public PageResponseDto<RoomResponseDto> search(CommonSearch<RoomSearch> search) {
        Pageable pageable =  new RoomSpecification().createPagination(search.getPageRequestDto());
        var page = repository.findAll(RoomSpecification.getRoom(search.getSearchObject()), pageable);
        log.info("content size {}" , (long) page.getContent().size());
        return mapper.toPageResponseDto(page.map(mapper::toResponseDto));
    }

}
