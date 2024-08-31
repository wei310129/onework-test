package com.example.oneworkTest.station.mapper;

import com.example.oneworkTest.api.request.WaterSpeedAquarkApiRequest;
import com.example.oneworkTest.station.entity.WaterSpeedAquark;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WaterSpeedAquarkMapper {
    WaterSpeedAquarkMapper INSTANCE = Mappers.getMapper(WaterSpeedAquarkMapper.class);

    WaterSpeedAquark fromRequest(WaterSpeedAquarkApiRequest request);
}
