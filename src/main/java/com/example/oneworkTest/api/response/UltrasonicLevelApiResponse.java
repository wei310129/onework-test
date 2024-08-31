package com.example.oneworkTest.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "超音波資訊")
@RequiredArgsConstructor
public class UltrasonicLevelApiResponse {
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "ECHO")
    private Double echo;

}
