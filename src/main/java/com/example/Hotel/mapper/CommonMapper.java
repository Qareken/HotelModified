package com.example.Hotel.mapper;

import com.example.Hotel.dto.PageResponseDto;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommonMapper<ENTITY, DTO, RESPONSE_DTO> {
    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

    RESPONSE_DTO toResponseDto(ENTITY entity);
    ENTITY merge(@MappingTarget ENTITY oldEntity, ENTITY newEntity);

    List<RESPONSE_DTO> toResponseDtoList(List<ENTITY> entityList);


    default <T> PageResponseDto<T> toPageResponseDto(Page<T> page) {
        PageResponseDto<T> pageResponseDto = new PageResponseDto<>();
        pageResponseDto.setContent(page.getContent());
        pageResponseDto.setPageNumber(page.getNumber());
        pageResponseDto.setPageSize(page.getSize());
        pageResponseDto.setTotalElements(page.getTotalElements());
        pageResponseDto.setTotalPages(page.getTotalPages());
        return pageResponseDto;
    }
}
