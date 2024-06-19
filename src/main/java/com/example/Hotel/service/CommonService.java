package com.example.Hotel.service;

import com.example.Hotel.dto.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService <REQ_DTO, RES_DTO, ENTITY, ID>{

    PageResponseDto<RES_DTO> findALL(Pageable pageable);
    RES_DTO findById(ID id);
    void deleteById(ID  id);
    RES_DTO save(REQ_DTO requestDto);
    RES_DTO update(REQ_DTO requestDto , ID id);
    ENTITY findEntityById(ID id);
}
