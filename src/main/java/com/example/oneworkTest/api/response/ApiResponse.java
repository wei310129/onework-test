package com.example.oneworkTest.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiResponse {
    List<RawApiResponse> raw = new ArrayList<>();
}
