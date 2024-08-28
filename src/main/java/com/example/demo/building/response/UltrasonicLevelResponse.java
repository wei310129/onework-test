package com.example.demo.building.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "超音波資訊")
@RequiredArgsConstructor
public class UltrasonicLevelResponse {
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "ECHO")
    private Double echo;

}
