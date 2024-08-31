package com.example.oneworkTest.station.request;

import com.example.oneworkTest.api.request.SensorApiRequest;
import com.example.oneworkTest.enumeration.AverageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@Schema(description = "搜尋建築監控資訊")
public class RawRequest {
    @Schema(description = "ID")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "站點ID")
    @JsonProperty("station_id")
    private String stationId;

    @Schema(description = "觀測日期時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("obs_time")
    private Date obsDateTime;

    @Schema(description = "觀測時間")
    private Date obsTime;

    @Schema(description = "CSQ")
    @JsonProperty("CSQ")
    private String csq;

    @Schema(description = "感測器")
    @JsonProperty("sensor")
    private SensorApiRequest sensor;

    @Schema(description = "日累積雨量mm")
    @JsonProperty("rain_d")
    private Double rainD;

    @Schema(description = "星期")
    @JsonProperty("day_of_week")
    private DayOfWeek dayOfWeek;

    @Schema(description = "時間區間-起始")
    @JsonProperty("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Schema(description = "時間區間-結束")
    @JsonProperty("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Schema(description = "平均單位")
    @JsonProperty("average_type")
    private AverageType averageType;

    @Schema(description = "是否要分尖峰/離峰")
    @JsonProperty("peak")
    private Boolean peak = false;
}
