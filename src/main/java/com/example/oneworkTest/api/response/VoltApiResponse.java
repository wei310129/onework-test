package com.example.oneworkTest.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "電壓資訊")
@RequiredArgsConstructor
public class VoltApiResponse {
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "V1")
    private Double v1;

    @Schema(description = "V2")
    private Double v2;

    @Schema(description = "V3")
    private Double v3;

    @Schema(description = "V4")
    private Double v4;

    @Schema(description = "太陽能板1電壓 V")
    private Double v5;

    @Schema(description = "太陽能板1電壓 V")
    private Double v6;

    @Schema(description = "日累積雨量mm")
    private Double v7;

}
