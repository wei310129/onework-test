package com.example.oneworkTest.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "水流資訊")
@NoArgsConstructor
public class WaterSpeedAquarkApiRequest {
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "表面流速m/s")
    private Double speed;
}
