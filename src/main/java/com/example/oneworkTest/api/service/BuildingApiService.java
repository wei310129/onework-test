package com.example.oneworkTest.api.service;

import com.example.oneworkTest.api.property.BuildingApiProperty;
import com.example.oneworkTest.api.request.ApiRequest;
import com.example.oneworkTest.api.request.RawApiRequest;
import com.example.oneworkTest.config.JacksonConfig;
import com.example.oneworkTest.station.entity.Raw;
import com.example.oneworkTest.station.mapper.RawMapper;
import com.example.oneworkTest.station.service.RawService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "用戶管理", description = "用戶相關的操作")
@Slf4j
@RequiredArgsConstructor
@Service
public class BuildingApiService {

    private final WebClient webClient;

    private final JacksonConfig jacksonConfig;

    private final RawService rawService;

    private final ObjectMapper objectMapper;

    private final RawMapper rawMapper;

    @Operation(summary = "打外部API", description = "阻塞式打外部API")
    public List<RawApiRequest> getExternalApiDataBlocking(final String url) {
        log.info("Start getting external api data {}", url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            ObjectMapper objectMapper = jacksonConfig.objectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            ApiRequest apiRequest = objectMapper.treeToValue(jsonNode, ApiRequest.class);
            List<RawApiRequest> rawApiRequestList = apiRequest.getRaw();
            log.info("Finished getting external api data {}", url);
            return rawApiRequestList;
        } catch (RestClientException | JsonProcessingException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "打外部API", description = "非阻塞式打外部API")
    public Mono<List<RawApiRequest>> getExternalApiDataNonBlocking(final String url) {
        log.info("Start getting external api data {}", url);
        try {
            return webClient.get()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .acceptCharset(StandardCharsets.UTF_8)
                    .retrieve()
                    .bodyToMono(String.class)  // 先取得響應為 String 格式
                    .flatMap(this::parseResponse)  // 解析 JSON 字串為所需物件
                    .doOnSuccess(result -> log.info("Finished getting external api data {}", url))
                    .doOnError(e -> log.error("Error while getting external api data: {}", e.getMessage()));
        } catch (RestClientException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    private Mono<List<RawApiRequest>> parseResponse(String responseBody) {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            ApiRequest apiRequest = objectMapper.treeToValue(jsonNode, ApiRequest.class);
            return Mono.just(apiRequest.getRaw());
        } catch (JsonProcessingException e) {
            log.error("Error parsing response body: {}", e.getMessage());
            return Mono.error(new RuntimeException("Failed to parse response", e));
        }
    }

    @Operation(summary = "打所有外部API", description = "阻塞式打外部API")
    public List<Raw> getAllExternalApiDataBlocking() {
        log.info("Start getting yesterday data.");
        final LocalDateTime today = LocalDate.now().atStartOfDay();
        final LocalDateTime yesterday = today.minusDays(1);
        // 取今日數據
        final List<RawApiRequest> rawApiRequests = BuildingApiProperty.apiUrls.stream().flatMap(url ->
                this.getExternalApiDataBlocking(url).stream()
                        .filter(rawApiRequest -> !rawApiRequest.getObsDateTime().isBefore(yesterday))
                        .filter(rawApiRequest -> rawApiRequest.getObsDateTime().isBefore(today))
                ).collect(Collectors.toList());
        final List<Raw> raws = rawService.insert(rawApiRequests);
        log.info("Finish getting yesterday data.");
        return raws;
    }

//    @Operation(summary = "打所有外部API", description = "非阻塞式打外部API")
//    public List<Raw> getAllExternalApiDataNonBlocking() {
//        log.info("Start getting yesterday data.");
//        final LocalDateTime today = LocalDate.now().atStartOfDay();
//        final LocalDateTime yesterday = today.minusDays(1);
//        // 取今日數據
//        final List<Raw> raws = Flux.fromIterable(BuildingApiProperty.apiUrls)
//                .parallel() // 使用 parallel() 開啟多執行緒
//                .runOn(Schedulers.boundedElastic()) // 使用 boundedElastic() 來支持 I/O 密集型操作
//                .flatMap(url ->
//                        this.getExternalApiDataNonBlocking(url)
//                                .map(rawApiRequests ->
//                                        rawApiRequests.stream()
//                                                .filter(rawApiRequest -> !rawApiRequest.getObsDateTime().isBefore(yesterday))
//                                                .filter(rawApiRequest -> rawApiRequest.getObsDateTime().isBefore(today))
//                                                .collect(Collectors.toList())))
//                .flatMap(rawList -> Flux.fromIterable(rawList) // 將過濾後的數據轉為 Flux
//                        .flatMap(rawApiRequest -> rawR2dbcRepository.saveAll(Flux.just(rawMapper.fromApiRequest(rawApiRequest))))) // 使用 flatMap 處理每個 rawApiRequest
//                .doOnComplete(() -> log.info("Finished processing all API data"))
//                .doOnError(e -> log.error("Error occurred during data fetching and processing: {}", e.getMessage()))
//                .sequential().collectList().block(); // 等待所有操作完成
//        log.info("Finish getting yesterday data.");
//        return raws;
//    }

}
