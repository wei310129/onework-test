package com.example.demo.building.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Getter
@Setter
@Schema(description = "水流資訊")
@NoArgsConstructor
public class WaterSpeedAquarkResponse {
    @Schema(description = "ID")
    private Long id;


    @Schema(description = "表面流速m/s")
    private Double speed;
}
