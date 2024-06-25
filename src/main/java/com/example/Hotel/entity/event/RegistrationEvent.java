package com.example.Hotel.entity.event;

import jakarta.persistence.Entity;
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
public class RegistrationEvent {
    private String id;
    private Long userId;
    private LocalDateTime createdTime;

}
