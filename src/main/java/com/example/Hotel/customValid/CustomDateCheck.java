package com.example.Hotel.customValid;

import com.example.Hotel.dto.bookingDto.BookingRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomDateCheck implements ConstraintValidator<DateCheck, BookingRequestDto> {
    @Override
    public boolean isValid(BookingRequestDto bookingRequestDto, ConstraintValidatorContext context) {
        if (bookingRequestDto.getArrivalDate() == null || bookingRequestDto.getDepartureDate() == null) {
            return true; // Не проверяем, если даты отсутствуют
        }
        return bookingRequestDto.getArrivalDate().isBefore(bookingRequestDto.getDepartureDate());
    }
}
