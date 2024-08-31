package com.example.oneworkTest.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "SensorApiResponse")
@RequiredArgsConstructor
public class SensorApiResponse {

    @Schema(description = "ID")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "電壓資訊")
    @JsonProperty("Volt")
    private VoltApiResponse volt;

    @Schema(description = "溫溼度資訊")
    @JsonProperty("StickTxRh")
    private StickTxRhApiResponse stickTxRh;

    @Schema(description = "超音波資訊")
    @JsonProperty("Ultrasonic_Level")
    private UltrasonicLevelApiResponse ultrasonicLevel;

    @Schema(description = "水流資訊")
    @JsonProperty("Water_speed_aquark")
    private WaterSpeedAquarkApiResponse waterSpeedAquark;
}
