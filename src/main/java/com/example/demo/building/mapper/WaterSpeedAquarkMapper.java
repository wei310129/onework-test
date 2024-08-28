package com.example.demo.building.mapper;

import com.example.demo.building.entity.WaterSpeedAquark;
import com.example.demo.building.response.WaterSpeedAquarkResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WaterSpeedAquarkMapper {
    WaterSpeedAquarkMapper INSTANCE = Mappers.getMapper(WaterSpeedAquarkMapper.class);

    WaterSpeedAquark fromRequest(WaterSpeedAquarkResponse request);
}
