package com.example.demo.building.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StickTxRhResponse {
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "濕度%")
    private Double rh;

    @Schema(description = "溫度℃")
    private Double tx;

}
