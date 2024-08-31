package com.example.oneworkTest.station.mapper;

import com.example.oneworkTest.api.request.SensorApiRequest;
import com.example.oneworkTest.station.entity.Sensor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {VoltMapper.class, StickTxRhMapper.class, UltrasonicLevelMapper.class, WaterSpeedAquarkMapper.class})
public interface SensorMapper {
    SensorMapper INSTANCE = Mappers.getMapper(SensorMapper.class);

    @Mapping(source = "stickTxRh", target = "stickTxRh")
    Sensor fromRequest(SensorApiRequest request);
}
