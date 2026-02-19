package com.example.apimonitoring.service;

import com.example.apimonitoring.model.MonitoredEndpoint;
import com.example.apimonitoring.repository.MonitoredEndpointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UptimeCheckService {
    private final MonitoredEndpointRepository endpointRepository;

    private final HttpClient  httpClient =  HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Scheduled(fixedRate = 60000)
    public void checkAllEndpoints() {
        log.info("Checking all endpoints");

        List<MonitoredEndpoint> endpoints = endpointRepository.findByIsActiveTrue();

        for (MonitoredEndpoint endpoint : endpoints) {
            checkSingleEndpoint(endpoint);
        }
    }

    private void checkSingleEndpoint(MonitoredEndpoint endpoint) {
        long startTime = System.currentTimeMillis();
        int statusCode = 0;
        boolean isUp = false;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint.getUrl()))
                    .GET()
                    .build();

            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());

            statusCode = response.statusCode();
            isUp = (statusCode >= 200 && statusCode < 300);

        } catch (Exception e) {
            log.error("Hata! {} adresine ulaşılamadı. Sebep: {}", endpoint.getUrl(), e.getMessage());
        } finally {
            long responseTime = System.currentTimeMillis() - startTime;

            log.info("Sonuç -> URL: {}, Durum: {}, Kod: {}, Yanıt Süresi: {}ms",
                    endpoint.getUrl(), (isUp ? "AYAKTA" : "ÇÖKTÜ"), statusCode, responseTime);

            // TODO: İlerleyen adımda bu veriyi Elasticsearch'e göndereceğiz
            // elasticsearchLogService.savePingResult(endpoint.getId(), endpoint.getUserId(), isUp, statusCode, responseTime);
        }
    }



}
