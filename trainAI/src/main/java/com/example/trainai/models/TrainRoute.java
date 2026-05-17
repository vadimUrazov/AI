package com.example.trainai.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainRoute {
    private String trainNumber;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private int availableSeats;
}
