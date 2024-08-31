package com.example.oneworkTest.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Schema(description = "建築監控資訊")
@RequiredArgsConstructor
public class RawApiRequest {
    @Schema(description = "ID")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "站點ID")
    @JsonProperty("station_id")
    private String stationId;

    @Schema(description = "觀測日期時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("obs_time")
    private LocalDateTime obsDateTime;

    @Schema(description = "CSQ")
    @JsonProperty("CSQ")
    private String csq;

    @Schema(description = "感測器")
    @JsonProperty("sensor")
    private SensorApiRequest sensor;

    @Schema(description = "日累積雨量mm")
    @JsonProperty("rain_d")
    private Double rainD;

    // 自己組
    @Schema(description = "星期")
    private DayOfWeek dayOfWeek;
}
