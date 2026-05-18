package com.example.trainai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class TrainAgentController {

    private final TrainAgentService agentService;

    @Autowired
    public TrainAgentController(TrainAgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/train-search")
    public ResponseEntity<String> searchTrains(@RequestBody Map<String, String> request) {
        String userQuery = request.get("query");
        String response = agentService.processTrainRequest(userQuery);
        return ResponseEntity.ok(response);
    }
}
