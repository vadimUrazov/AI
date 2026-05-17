package com.example.trainai.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainSearchRequest {
    private String from;
    private String to;
    private String date;
}
