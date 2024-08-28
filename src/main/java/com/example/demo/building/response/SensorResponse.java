package com.example.demo.building.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class SensorResponse {

    @Schema(description = "ID")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "電壓資訊")
    @JsonProperty("Volt")
    private VoltResponse volt;

    @Schema(description = "溫溼度資訊")
    @JsonProperty("StickTxRh")
    private StickTxRhResponse stickTxRh;

    @Schema(description = "超音波資訊")
    @JsonProperty("Ultrasonic_Level")
    private UltrasonicLevelResponse ultrasonicLevel;

    @Schema(description = "水流資訊")
    @JsonProperty("Water_speed_aquark")
    private WaterSpeedAquarkResponse waterSpeedAquark;
}
