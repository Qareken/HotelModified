package com.example.Hotel.service.impl;

import com.example.Hotel.dto.hotelDto.HotelRate;
import com.example.Hotel.dto.hotelDto.HotelRequestDto;
import com.example.Hotel.dto.hotelDto.HotelResponseDto;
import com.example.Hotel.entity.Hotel;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.mapper.HotelMapper;
import com.example.Hotel.repository.CityRepository;
import com.example.Hotel.repository.HotelRepository;
import com.example.Hotel.service.HotelService;
import org.springframework.stereotype.Service;


@Service

public class HotelServiceImpl extends CommonServiceImpl<HotelRequestDto, HotelResponseDto, Hotel, Long> implements HotelService {
    private final CityRepository cityRepository;
    public HotelServiceImpl(HotelRepository repository, HotelMapper hotelMapper, CityRepository cityRepository) {
        super(repository, hotelMapper);
        this.cityRepository = cityRepository;
    }

    @Override
    public HotelResponseDto save(HotelRequestDto hotelRequestDto) {
        var hotel = mapper.toEntity(hotelRequestDto);
        var city = cityRepository.findCityByName(hotel.getCity().getName()).orElseGet(()->cityRepository.save(hotel.getCity()));
        hotel.setCity(city);
        if(((HotelRepository)repository).existsHotelByNameAndCity(hotel.getName(), city)){
            throw new BadRequestException("Hotel is existed");
        }else {
            return mapper.toResponseDto(repository.save(hotel));
        }

    }
    @Override
    public HotelResponseDto changeRate(HotelRate hotelRate) {
        var hotel = findEntityById(hotelRate.hotelId());
        //totalRating = rating × numberOfRating
        var totalRating = hotel.getRating() * hotel.getNumberOfRatings();
        //totalRating = totalRating − rating + newMark
        totalRating = totalRating - hotel.getRating() + hotelRate.rating();
        //rating = totalRating / numberOfRating
        hotel.setRating(totalRating/hotel.getNumberOfRatings());
        //numberOfRating = numberOfRating + 1
        hotel.setNumberOfRatings(hotel.getNumberOfRatings() + 1);

        return mapper.toResponseDto(repository.save(hotel));
    }
}
