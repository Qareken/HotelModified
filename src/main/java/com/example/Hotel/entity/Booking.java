package com.example.Hotel.entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Arrival date is mandatory")
    @FutureOrPresent(message = "Arrival date must be in the present or future")
    private LocalDateTime arrivalDate;
    @NotNull(message = "Departure date is mandatory")
    @FutureOrPresent(message = "Departure date must be in the present or future")
    private LocalDateTime departureDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Users user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonBackReference
    private Room room;
}
