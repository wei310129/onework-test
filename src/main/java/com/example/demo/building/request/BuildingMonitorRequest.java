package com.example.demo.building.request;

import com.example.demo.building.response.SensorResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Schema(description = "搜尋建築監控資訊")
public class BuildingMonitorRequest {
    @Schema(description = "ID")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "站點ID")
    @JsonProperty("station_id")
    private String stationId;

    @Schema(description = "觀測日期時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("obs_time")
    private LocalDateTime obsDateTime;

    @Schema(description = "觀測時間")
    private LocalTime obsTime;

    @Schema(description = "CSQ")
    @JsonProperty("CSQ")
    private String csq;

    @Schema(description = "感測器")
    @JsonProperty("sensor")
    private SensorResponse sensor;

    @Schema(description = "日累積雨量mm")
    @JsonProperty("rain_d")
    private Double rainD;

    @Schema(description = "星期")
    private DayOfWeek dayOfWeek;

    @Schema(description = "時間範圍")
    private Boolean getPeakHours;
}
