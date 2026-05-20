package com.example.busai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BusAgentController {
    private final BusAgentService agentService;

    @Autowired
    public BusAgentController(BusAgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/bus-search")
    public ResponseEntity<String> searchTrains(@RequestBody Map<String, String> request) {
        String userQuery = request.get("query");
        String response = agentService.processBusRequest(userQuery);
        return ResponseEntity.ok(response);
    }
}
