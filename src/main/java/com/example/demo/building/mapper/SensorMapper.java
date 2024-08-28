package com.example.demo.building.mapper;

import com.example.demo.building.entity.Sensor;
import com.example.demo.building.response.SensorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {VoltMapper.class, StickTxRhMapper.class, UltrasonicLevelMapper.class, WaterSpeedAquarkMapper.class})
public interface SensorMapper {
    SensorMapper INSTANCE = Mappers.getMapper(SensorMapper.class);

    @Mapping(source = "stickTxRh", target = "stickTxRh")
    Sensor fromRequest(SensorResponse request);
}
