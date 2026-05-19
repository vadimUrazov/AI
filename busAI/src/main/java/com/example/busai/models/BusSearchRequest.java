package com.example.busai.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusSearchRequest {
    private String from;
    private String to;
    private String date;

}
