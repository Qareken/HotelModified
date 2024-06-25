package com.example.Hotel.entity.event;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "registrationTask")
public class BookingEvent {
    private String id;
    private Long userId;
    private Long roomId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private LocalDateTime createdTime;
}
