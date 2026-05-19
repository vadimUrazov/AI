package com.example.busai.service;

import com.example.busai.models.BusRoute;
import com.example.busai.models.BusSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BusApiService {

    private final RestTemplate restTemplate;

    @Autowired
    public BusApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BusRoute> searchBus(BusSearchRequest request) {
        // Здесь — интеграция с реальным API (РЖД)
        //запрос:
        String url = String.format(
                "https://bus.tutu.ru/",
                request.getFrom(),
                request.getTo(),
                request.getDate()
        );
        BusRoute[] response = restTemplate.getForObject(url, BusRoute[].class);
        return Arrays.asList(response);
    }
}
