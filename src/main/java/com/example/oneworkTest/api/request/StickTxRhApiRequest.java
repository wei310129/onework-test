package com.example.oneworkTest.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "StickTxRhApiRequest")
@RequiredArgsConstructor
public class StickTxRhApiRequest {
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "濕度%")
    private Double rh;

    @Schema(description = "溫度℃")
    private Double tx;

}
