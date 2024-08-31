package com.example.oneworkTest.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Schema(description = "建築監控資訊")
@RequiredArgsConstructor
public class RawApiResponse implements Serializable {
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

    @Schema(description = "觀測日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonProperty("obs_d")
    private LocalDate obsDate;

    @Schema(description = "觀測時間")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonProperty("obs_t")
    private LocalTime obsTime;

    @Schema(description = "CSQ")
    @JsonProperty("CSQ")
    private String csq;

    @Schema(description = "感測器")
    @JsonProperty("sensor")
    private SensorApiResponse sensor;

    @Schema(description = "日累積雨量mm")
    @JsonProperty("rain_d")
    private Double rainD;

    // 自己組
    @Schema(description = "星期")
    private DayOfWeek dayOfWeek;

    @Schema(description = "是否為尖峰")
    private Boolean isPeak;
}
