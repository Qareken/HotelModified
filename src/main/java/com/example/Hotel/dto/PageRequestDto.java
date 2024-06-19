package com.example.Hotel.dto;

import lombok.Data;

@Data
public class PageRequestDto {
    private int page;
    private int size;
    private String sortDirection;
    private String sortBy;
}
