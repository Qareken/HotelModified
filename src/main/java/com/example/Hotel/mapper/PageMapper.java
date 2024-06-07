package com.example.Hotel.mapper;

import com.example.Hotel.dto.PageResponseDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {

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