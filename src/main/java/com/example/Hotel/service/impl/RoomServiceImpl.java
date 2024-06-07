package com.example.Hotel.service.impl;

import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.dto.RoomRequestDto;
import com.example.Hotel.dto.RoomResponseDto;
import com.example.Hotel.entity.Room;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.exception.EntityNotFoundException;
import com.example.Hotel.mapper.PageMapper;
import com.example.Hotel.mapper.RoomMapper;
import com.example.Hotel.repository.HotelRepository;
import com.example.Hotel.repository.RoomRepository;
import com.example.Hotel.service.RoomService;
import com.example.Hotel.service.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;
    private final PageMapper pageMapper;
    @Override
    public RoomResponseDto save(RoomRequestDto roomRequestDto) {
        var room = roomMapper.toEntity(roomRequestDto);
        var hotel = hotelRepository.findById(roomRequestDto.getHotelId());
        if(hotel.isPresent()){
            room.setHotel(hotel.get());
            return roomMapper.toResponseDTO(roomRepository.save(room));
        }else {
            throw new BadRequestException(MessageFormat.format("Hotel with this id {} has not been registered", roomRequestDto.getHotelId()));
        }

    }

    @Override
    public PageResponseDto<RoomResponseDto> findAll(Pageable pageable) {
        return pageMapper.toPageResponseDto(roomRepository.findAll(pageable).map(roomMapper::toResponseDTO));
    }

    @Override
    public RoomResponseDto findById(Long id) {
        return roomMapper.toResponseDTO(findRoomById(id));
    }
    private Room findRoomById(Long id){
        return roomRepository.findById(id).orElseThrow(()->new EntityNotFoundException(MessageFormat.format("Room with this id {} not found", id)));
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public RoomResponseDto update(RoomRequestDto roomRequestDto, Long id) {
        var existedRoom = findRoomById(id);
        var hotel = roomMapper.toEntity(roomRequestDto);
        BeanUtils.copyNonNullProperties(hotel, existedRoom);
        return roomMapper.toResponseDTO(roomRepository.save(existedRoom));
    }
}
