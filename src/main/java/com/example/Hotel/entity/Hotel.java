package com.example.Hotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String title;
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
    private Long distance;
    @Min(1)
    @Max(5)
    private int rating;
    @Min(0)
    private int numberOfRatings;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms = new HashSet<>();

}
