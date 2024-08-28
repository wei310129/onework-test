package com.example.demo.building.api;

import com.example.demo.building.response.ApiResponse;
import com.example.demo.building.response.RawResponse;
import com.example.demo.config.JacksonConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "用戶管理", description = "用戶相關的操作")
@Slf4j
@RequiredArgsConstructor
@Service
public class BuildingApiService {

    private final WebClient webClient;

    private final JacksonConfig jacksonConfig;

    @Operation(summary = "打外部API", description = "阻塞式打外部API")
    public List<RawResponse> getExternalApiData() throws Exception {
        final String url = "https://app.aquark.com.tw/api/raw/Angle2024/240627";
        log.info("getting external api data {}", url);
//        final ApiResponse apiResponse = webClient.get().uri(url).retrieve().bodyToMono(ApiResponse.class).block();
//        if (apiResponse != null) {
//            log.info("successfully getting external api data {}", url);
//            return apiResponse.getRaw();
//        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            ObjectMapper objectMapper = jacksonConfig.objectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
//            ApiResponse apiResponse = objectMapper.convertValue(jsonNode, ApiResponse.class);

            ApiResponse apiResponse = objectMapper.treeToValue(jsonNode, ApiResponse.class);
            return  apiResponse.getRaw();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

//        log.error("error");
//        return new ArrayList<>();
    }

}
