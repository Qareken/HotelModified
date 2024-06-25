package com.example.Hotel.dto.kafka;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class KafkaMessage {
    private Long id;
    private String message;
}
