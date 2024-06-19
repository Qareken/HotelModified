package com.example.Hotel.dto.bookingDto;

import com.example.Hotel.customValid.DateCheck;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@DateCheck
public class BookingRequestDto {
    @NotNull(message = "Arrival date is mandatory")
    @FutureOrPresent(message = "Arrival date must be in the present or future")
    private LocalDateTime arrivalDate;
    @NotNull(message = "Departure date is mandatory")
    @FutureOrPresent(message = "Departure date must be in the present or future")
    private LocalDateTime departureDate;
    @NotNull(message = "User ID is mandatory")
    private Long userId;
    @NotNull(message = "Room ID is mandatory")
    private Long roomId;
}
