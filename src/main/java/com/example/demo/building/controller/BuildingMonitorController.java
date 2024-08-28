package com.example.demo.building.controller;

import com.example.demo.building.api.BuildingApiService;
import com.example.demo.building.entity.Raw;
import com.example.demo.building.mapper.RawMapper;
import com.example.demo.building.response.ApiResponse;
import com.example.demo.building.response.RawResponse;
import com.example.demo.building.service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "紀錄建築物數據", description = "紀錄建築物數據")
@Slf4j
@RestController
@RequestMapping("/building-monitor")
@RequiredArgsConstructor
public class BuildingMonitorController {

    private final BuildingApiService buildingApiService;

    private final BuildingService buildingService;

    private final RawMapper rawMapper;

    @Operation(summary = "紀錄建築物數據")
    @PostMapping
    public ResponseEntity<List<RawResponse>> inputBuildingData(@RequestBody ApiResponse response) {
        final List<Raw> raws = buildingService.insert(response.getRaw());
        final List<RawResponse> rawResponses = raws.stream().map(rawMapper::toRequest).collect(Collectors.toList());
        return ResponseEntity.ok(rawResponses);
    }

    @Operation(summary = "紀錄建築物數據")
    @PostMapping("/api")
    public ResponseEntity<List<RawResponse>> recordApiBuildingData() throws Exception {
        final LocalDateTime yesterday = LocalDate.now().atStartOfDay().minusDays(1L);
        final List<RawResponse> buildingMonitorRequests = buildingApiService.getExternalApiData();
        final List<Raw> raws = buildingService.insert(buildingMonitorRequests);
        final List<RawResponse> rawResponses = raws.stream().map(rawMapper::toRequest).collect(Collectors.toList());
        return ResponseEntity.ok(rawResponses);
    }

    @Operation(summary = "取得建築物數據")
    @GetMapping("/api")
    public ResponseEntity<List<RawResponse>> queryApiBuildingData() {
        final List<Raw> raws = buildingService.queryAll();
        final List<RawResponse> rawResponses = raws.stream().map(rawMapper::toRequest).collect(Collectors.toList());
        return ResponseEntity.ok(rawResponses);
    }
}
