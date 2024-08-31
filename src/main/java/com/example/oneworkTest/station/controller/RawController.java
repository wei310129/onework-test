package com.example.oneworkTest.station.controller;

import com.example.oneworkTest.api.request.RawApiRequest;
import com.example.oneworkTest.api.service.BuildingApiService;
import com.example.oneworkTest.station.entity.Raw;
import com.example.oneworkTest.station.mapper.RawMapper;
import com.example.oneworkTest.api.request.ApiRequest;
import com.example.oneworkTest.station.request.RawRequest;
import com.example.oneworkTest.api.response.RawApiResponse;
import com.example.oneworkTest.station.service.RawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.*;

@Tag(name = "紀錄建築物數據", description = "紀錄建築物數據")
@Slf4j
@RestController
@RequestMapping("/building-monitor")
@RequiredArgsConstructor
public class RawController {

    private final BuildingApiService buildingApiService;

    private final RawService rawService;

    private final RawMapper rawMapper;

    @Operation(summary = "紀錄建築物數據")
    @PostMapping
    public ResponseEntity<List<RawApiResponse>> inputRawData(@RequestBody ApiRequest request) {
        final List<Raw> raws = rawService.insert(request.getRaw());
        final List<RawApiResponse> rawApiResponses = raws.stream().map(rawMapper::toApiResponse).collect(Collectors.toList());
        return ResponseEntity.ok(rawApiResponses);
    }

    @Operation(summary = "阻塞式打api紀錄今日建築物數據")
    @PostMapping("/api")
    public ResponseEntity<List<RawApiResponse>> recordApiRawDataBlocking() {
        final List<Raw> raws = buildingApiService.getAllExternalApiDataBlocking();
        final List<RawApiResponse> rawApiResponses = raws.stream().map(rawMapper::toApiResponse).collect(Collectors.toList());
        return ResponseEntity.ok(rawApiResponses);
    }

    @Operation(summary = "非阻塞式打api紀錄今日建築物數據")
    @PostMapping("/api/non-blocking")
    public ResponseEntity<List<RawApiResponse>> recordApiRawDataNonBlocking() {
        final List<Raw> raws = buildingApiService.getAllExternalApiDataBlocking();
        final List<RawApiResponse> rawApiResponses = raws.stream().map(rawMapper::toApiResponse).collect(Collectors.toList());
        return ResponseEntity.ok(rawApiResponses);
    }

    @Operation(summary = "取得全部建築物數據")
    @GetMapping("/all")
    public ResponseEntity<List<RawApiResponse>> queryAllApiRawData() {
        final List<Raw> raws = rawService.queryAll();
        final List<RawApiResponse> rawApiResponses = raws.stream().map(rawMapper::toApiResponse).collect(Collectors.toList());
        return ResponseEntity.ok(rawApiResponses);
    }

    /**
     * 將資料以時間分類計算區分
     * 尖峰時間（週一～週三 : 7:30 ~17:30。 週四 週五全天）
     * 離峰時間（週一～週三 : 00:00 ~7:30 週一～週三 17:30~00:00。  週六 週日 全天）
     * 可供搜尋尖峰時間與離峰時間內不同數據
     */
    @Operation(summary = "取得特定時間建築物數據")
    @GetMapping("/spec-time")
    public ResponseEntity<List<RawApiResponse>> querySpecApiRawData(@RequestBody RawRequest request) {
        final List<Raw> raws = rawService.query(request);
        final List<RawApiResponse> rawApiResponses = raws.stream().map(rawMapper::toApiResponse)
                // 分辨尖峰/離峰
                .peek(res -> {
                            if (request.getPeak()) {
                                res.setIsPeak((rawService.isInDayRange(MONDAY, WEDNESDAY, res.getDayOfWeek()) &&
                                        rawService.isInTimeRange(LocalTime.of(7, 30), LocalTime.of(17, 30), res.getObsTime())) ||
                                        rawService.isInDayRange(THURSDAY, FRIDAY, res.getDayOfWeek()));
                            }
                        })
                .collect(Collectors.toList());
        return ResponseEntity.ok(rawApiResponses);
    }

    /**
     * 計算各項數值每天數值加總成果｜每小時平均 及每日平均
     */
    @Operation(summary = "取得每日/小時建築物數據總和平均")
    @GetMapping("/average")
    public ResponseEntity<List<RawApiResponse>> queryAverageApiRawData(@RequestBody RawRequest request) {
        final List<Raw> raws = rawService.query(request);
        final Map<LocalDateTime, List<RawApiResponse>> dateListMap = raws.stream()
                .map(rawMapper::toApiResponse)
                .peek(res -> {
                    switch (request.getAverageType()) {
                        case HOUR:
                            res.setObsDateTime(res.getObsDateTime().withMinute(0).withSecond(0));
                            break;
                        case DATE:
                        default:
                            res.setObsDateTime(res.getObsDateTime().withHour(0).withMinute(0).withSecond(0));
                    }
                })
                .collect(Collectors.groupingBy(RawApiResponse::getObsDateTime));
        final List<RawApiResponse> rawAverage = dateListMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .map(entry -> rawService.countRawAverage(entry.getValue())).collect(Collectors.toList());
        return ResponseEntity.ok(rawAverage);
    }
}
