package com.example.Hotel.service.impl;

import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.exception.EntityNotFoundException;
import com.example.Hotel.mapper.CommonMapper;
import com.example.Hotel.repository.CommonRepository;
import com.example.Hotel.service.CommonService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;


import java.text.MessageFormat;


@RequiredArgsConstructor
public abstract class CommonServiceImpl<REQ_DTO, RES_DTO, ENTITY, ID> implements CommonService<REQ_DTO, RES_DTO, ENTITY, ID> {
    protected final CommonRepository<ENTITY, ID> repository;
    protected final CommonMapper<ENTITY, REQ_DTO, RES_DTO> mapper;

    @Override
    public PageResponseDto<RES_DTO> findALL(Pageable pageable) {
        return mapper.toPageResponseDto(repository.findAll(pageable).map(mapper::toResponseDto));
    }

    @Override
    public ENTITY findEntityById(ID id) {
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException(MessageFormat.format("Object not found with {} : id", id)));
    }

    @Override
    public RES_DTO findById(ID id) {
        return mapper.toResponseDto(findEntityById(id));
    }

    @Override
    public RES_DTO save(REQ_DTO requestDto) {
        return mapper.toResponseDto(repository.save(mapper.toEntity(requestDto)));
    }

    @Override
    public RES_DTO update(REQ_DTO requestDto, ID id) {
        var oldEntity = findEntityById(id);
        var newEntity = mapper.toEntity(requestDto);
        oldEntity = mapper.merge(oldEntity, newEntity);
        return mapper.toResponseDto(repository.save(oldEntity));
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
