package com.example.Hotel.service.impl;

import com.example.Hotel.configuration.HotelSpecificationExecutor;
import com.example.Hotel.dto.filter.CommonSearch;
import com.example.Hotel.dto.filter.PageRequestDto;
import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.dto.filter.HotelSearch;
import com.example.Hotel.dto.hotelDto.HotelRate;
import com.example.Hotel.dto.hotelDto.HotelRequestDto;
import com.example.Hotel.dto.hotelDto.HotelResponseDto;
import com.example.Hotel.entity.Hotel;
import com.example.Hotel.exception.BadRequestException;
import com.example.Hotel.mapper.HotelMapper;
import com.example.Hotel.repository.CityRepository;
import com.example.Hotel.repository.HotelRepository;
import com.example.Hotel.service.HotelService;
import com.example.Hotel.service.specification.HotelSpecification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class HotelServiceImpl extends CommonServiceImpl<HotelRequestDto, HotelResponseDto, Hotel, Long> implements HotelService {
    private final CityRepository cityRepository;
    private final HotelSpecificationExecutor executor;
    public HotelServiceImpl(HotelRepository repository, HotelMapper hotelMapper, CityRepository cityRepository, HotelSpecificationExecutor executor) {
        super(repository, hotelMapper);
        this.cityRepository = cityRepository;
        this.executor = executor;
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

        if(hotel.getRating()==0){
            hotel.setRating(hotelRate.rating());
            hotel.setNumberOfRatings(1);
        }else {
            var totalRating = hotel.getRating() * hotel.getNumberOfRatings();
            //totalRating = rating × numberOfRating

            //totalRating = totalRating − rating + newMark
            totalRating = totalRating - hotel.getRating() + hotelRate.rating();
            //rating = totalRating / numberOfRating
            hotel.setRating(totalRating/hotel.getNumberOfRatings());
            //numberOfRating = numberOfRating + 1
            hotel.setNumberOfRatings(hotel.getNumberOfRatings() + 1);
        }


        return mapper.toResponseDto(repository.save(hotel));
    }

    @Override
    public PageResponseDto<HotelResponseDto> search(CommonSearch<HotelSearch> hotelSearch) {
        Pageable pageable =  new HotelSpecification().createPagination(hotelSearch.getPageRequestDto());
        Specification<Hotel> specification = HotelSpecification.getHotels(hotelSearch.getSearchObject());
        log.info("Executing specification with pageable: {}", pageable);
        log.info("Specification: {}", specification);

        Page<Hotel> result = repository.findAll(specification, pageable);
        log.info("Found {} hotels matching the criteria using repository", result.getTotalElements());
        result.getContent().forEach(hotel -> log.info("Hotel name: {}", hotel));
        return mapper.toPageResponseDto(result.map(mapper::toResponseDto));
    }
}
