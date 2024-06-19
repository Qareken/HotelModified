package com.example.Hotel.mapper;

import com.example.Hotel.dto.hotelDto.HotelRequestDto;
import com.example.Hotel.dto.hotelDto.HotelResponseDto;
import com.example.Hotel.entity.City;
import com.example.Hotel.entity.Hotel;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper extends CommonMapper<Hotel, HotelRequestDto, HotelResponseDto>{
    @Mapping(source = "city.name", target = "name")
    HotelRequestDto toDto(Hotel hotel);
    @InheritInverseConfiguration
    @Mapping(source = "cityName", target = "city", qualifiedByName = "nameToCity")
    Hotel toEntity(HotelRequestDto hotelRequestDto);
    @Mapping(source = "city.name", target = "city")
    HotelResponseDto toResponseDto(Hotel hotel);
    @Named("nameToCity")
    default City nameToCity(String cityName){
        City city = new City();
        city.setName(cityName);
        return city;
    }

}
