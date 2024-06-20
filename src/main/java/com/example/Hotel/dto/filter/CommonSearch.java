package com.example.Hotel.dto.filter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Data
@Getter
@Setter
public class CommonSearch <T>{

    private T  searchObject;
    private PageRequestDto pageRequestDto;
}
