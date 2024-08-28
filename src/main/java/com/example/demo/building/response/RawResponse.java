package com.example.demo.building.response;

import com.example.demo.building.entity.Sensor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Schema(description = "建築監控資訊")
@RequiredArgsConstructor
public class RawResponse {
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
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonProperty("obs_time")
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

    // 自己組
    @Schema(description = "星期")
    private DayOfWeek dayOfWeek;
}
