package com.example.busai.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusRoute {
    private String busCompany;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private int availableSeats;

}
