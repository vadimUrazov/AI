package com.example.busai.service;

import com.example.busai.models.BusRoute;
import com.example.busai.models.BusSearchRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusAgentService {

    private final ChatClient chatClient;
    private final BusApiService busApiService;

    @Autowired
    public BusAgentService(ChatClient chatClient, BusApiService busApiService) {
        this.chatClient = chatClient;
        this.busApiService = busApiService;
    }

    public String processBusRequest(String userQuery) {
        String systemPrompt = """
                Ты — AI‑агент для поиска автобусных рейсов.
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
        String extractedParams = chatClient.prompt(
                systemPrompt + "\n\nПользовательский запрос: " + userQuery
        ).call().content();

        try {
            BusSearchRequest request = new ObjectMapper()
                    .readValue(extractedParams, BusSearchRequest.class);
            List<BusRoute> routes = busApiService.searchBus(request);
            return formatResponse(routes);
        } catch (Exception e) {
            return "Не удалось обработать запрос. Проверьте корректность данных.";
        }
    }

    private String formatResponse(List<BusRoute> routes) {
        if (routes.isEmpty()) {
            return "По вашему запросу рейсов не найдено.";
        }
        return routes.stream()
                .map(route -> String.format(
                        "Автобус компания%s: %s → %s, цена: %.2f руб., мест: %d",
                        route.getBusCompany(),
                        route.getDepartureTime(),
                        route.getArrivalTime(),
                        route.getPrice(),
                        route.getAvailableSeats()
                ))
                .collect(Collectors.joining("\n"));
    }
}
