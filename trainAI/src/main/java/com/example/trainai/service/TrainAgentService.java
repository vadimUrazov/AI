package com.example.trainai.service;

import com.example.trainai.models.TrainRoute;
import com.example.trainai.models.TrainSearchRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainAgentService {

    private final ChatClient chatClient;
    private final TrainApiService trainApiService;

    @Autowired
    public TrainAgentService(ChatClient chatClient, TrainApiService trainApiService) {
        this.chatClient = chatClient;
        this.trainApiService = trainApiService;
    }

    public String processTrainRequest(String userQuery) {
        String systemPrompt = """
                Ты — AI‑агент для поиска железнодорожных рейсов.
                Задача: извлечь из запроса пользователя параметры:
                - пункт отправления (from)
                - пункт назначения (to)
                - дата поездки (date)
                
                Формат ответа — строго JSON:
                {
                  "from": "Москва",
                  "to": "Санкт‑Петербург",
                  "date": "2024-12-25"
                }
                
                Если какой‑то параметр отсутствует, поставь null.
                Не добавляй пояснений, только JSON.
                """;

        String extractedParams = chatClient.prompt(systemPrompt)
                .user(userQuery)
                .call().content();

        try {
            TrainSearchRequest request = new ObjectMapper()
                    .readValue(extractedParams, TrainSearchRequest.class);
            List<TrainRoute> routes = trainApiService.searchTrains(request);
            return formatResponse(routes);
        } catch (Exception e) {
            return "Не удалось обработать запрос. Проверьте корректность данных.";
        }
    }

    private String formatResponse(List<TrainRoute> routes) {
        if (routes.isEmpty()) {
            return "По вашему запросу рейсов не найдено.";
        }
        return routes.stream()
                .map(route -> String.format(
                        "Поезд №%s: %s → %s, цена: %.2f руб., мест: %d",
                        route.getTrainNumber(),
                        route.getDepartureTime(),
                        route.getArrivalTime(),
                        route.getPrice(),
                        route.getAvailableSeats()
                ))
                .collect(Collectors.joining("\n"));
    }
}
