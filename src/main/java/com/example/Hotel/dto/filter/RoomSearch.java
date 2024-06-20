package com.example.Hotel.dto.filter;

import com.example.Hotel.customValid.DateCheck;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@DateCheck
public class RoomSearch {
    private Long id;

    private String title;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private int maxOccupancy;



    private LocalDateTime arrivalDate;

    private LocalDateTime departureDate;
    private Long hotelId;
}
