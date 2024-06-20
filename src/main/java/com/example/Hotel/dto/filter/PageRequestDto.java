package com.example.Hotel.dto.filter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PageRequestDto {
    private int page;
    private int size;
    private String sortDirection;
    private String sortBy;
}
