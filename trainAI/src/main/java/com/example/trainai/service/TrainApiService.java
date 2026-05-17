package com.example.trainai.service;

import com.example.trainai.models.TrainRoute;
import com.example.trainai.models.TrainSearchRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TrainApiService {

    private final RestTemplate restTemplate;

    public TrainApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TrainRoute> searchTrains(TrainSearchRequest request) {
        // Здесь — интеграция с реальным API (РЖД)
        //запрос:
        String url = String.format(
                "https://www.rzd.ru/#",
                request.getFrom(),
                request.getTo(),
                request.getDate()
        );
       TrainRoute[] response = restTemplate.getForObject(url, TrainRoute[].class);
        return Arrays.asList(response);
    }
}
