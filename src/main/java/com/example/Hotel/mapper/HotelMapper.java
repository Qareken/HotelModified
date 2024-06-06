package com.example.Hotel.mapper;

import com.example.Hotel.dto.HotelRequestDto;
import com.example.Hotel.dto.HotelResponseDto;
import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.entity.City;
import com.example.Hotel.entity.Hotel;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {
    @Mapping(source = "city.name", target = "name")
//    @Mapping(target = "rating", ignore = true)
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "numberOfRatings", ignore = true)
    HotelRequestDto toHotelRequestDto(Hotel hotel);
    @InheritInverseConfiguration
    @Mapping(source = "cityName", target = "city", qualifiedByName = "nameToCity")
    Hotel toEntity(HotelRequestDto hotelRequestDto);
    @Mapping(source = "city.name", target = "city")
    HotelResponseDto toHotelResponseDto(Hotel hotel);
    List<HotelResponseDto> toHotelResponseDtoList(List<Hotel> hotelList);
    default PageResponseDto<HotelResponseDto> toPageResponseDto(Page<Hotel> hotelPage) {
        List<HotelResponseDto> hotelResponseDtoList = toHotelResponseDtoList(hotelPage.getContent());

        PageResponseDto<HotelResponseDto> pageResponseDto = new PageResponseDto<>();
        pageResponseDto.setContent(hotelResponseDtoList);
        pageResponseDto.setPageNumber(hotelPage.getNumber());
        pageResponseDto.setPageSize(hotelPage.getSize());
        pageResponseDto.setTotalElements(hotelPage.getTotalElements());
        pageResponseDto.setTotalPages(hotelPage.getTotalPages());

        return pageResponseDto;
    }
    @Named("nameToCity")
    default City nameToCity(String cityName){
        City city = new City();
        city.setName(cityName);
        return city;
    }

}
