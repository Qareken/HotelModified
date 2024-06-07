package com.example.Hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer numberOfRoom;
    private BigDecimal price;
    private Integer maxOccupancy;
    @ElementCollection
    @CollectionTable(name = "room_unavailable_dates", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "unavailable_date")
    private Set<LocalDateTime> unavailableDates;
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
}
