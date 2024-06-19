package com.example.Hotel.dto.hotelDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record HotelRate (@Min(value = 1, message = "hotelId не может быть меньше 1")
                         @NotNull(message = "hotelId must not be empty")
                         Long hotelId,
                         @Min(value = 1, message = "Оценка не может быть меньше 1")
                         @Max(value = 5, message = "Оценка не может быть больше 5")
                         @NotNull(message = "rating must not be empty")
                         Integer rating){
}
