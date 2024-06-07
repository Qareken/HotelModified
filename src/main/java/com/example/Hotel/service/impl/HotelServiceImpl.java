package com.example.Hotel.service.impl;

import com.example.Hotel.dto.HotelRequestDto;
import com.example.Hotel.dto.HotelResponseDto;
import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.entity.Hotel;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.exception.EntityNotFoundException;
import com.example.Hotel.mapper.HotelMapper;
import com.example.Hotel.mapper.PageMapper;
import com.example.Hotel.repository.CityRepository;
import com.example.Hotel.repository.HotelRepository;
import com.example.Hotel.service.HotelService;
import com.example.Hotel.service.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;
    private final HotelMapper hotelMapper;
    private final PageMapper pageMapper;
    @Override
    public HotelResponseDto save(HotelRequestDto hotelRequestDto) {
        var hotel = hotelMapper.toEntity(hotelRequestDto);
        var city = cityRepository.findCityByName(hotel.getCity().getName()).orElseGet(()->cityRepository.save(hotel.getCity()));
        hotel.setCity(city);
        if(hotelRepository.existsHotelByNameAndCity(hotel.getName(), city)){
            throw new BadRequestException("Hotel is existed");
        }else {
            return hotelMapper.toHotelResponseDto(hotelRepository.save(hotel));
        }

    }
    @Override
    public PageResponseDto<HotelResponseDto>findAll(Pageable pageable) {
        return pageMapper.toPageResponseDto(hotelRepository.findAll(pageable).map(hotelMapper::toHotelResponseDto));
    }
    @Override
    public HotelResponseDto findById(Long id) {
        return hotelMapper.toHotelResponseDto(findHotelById(id));
    }
    private Hotel findHotelById(Long id){
        return hotelRepository.findById(id).orElseThrow(()->new EntityNotFoundException(MessageFormat.format("Hotel not found with {} id", id)));
    }
    @Override
    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }
    @Override
    public HotelResponseDto update(HotelRequestDto hotelRequestDto, Long id) {
        var existedHotel = findHotelById(id);
        var hotel = hotelMapper.toEntity(hotelRequestDto);
        BeanUtils.copyNonNullProperties(hotel, existedHotel);
        return hotelMapper.toHotelResponseDto(hotelRepository.save(existedHotel));
    }
}
