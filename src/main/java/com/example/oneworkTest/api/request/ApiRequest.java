package com.example.oneworkTest.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiRequest {
    List<RawApiRequest> raw = new ArrayList<>();
}
