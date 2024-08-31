package com.example.oneworkTest.station.mapper;

import com.example.oneworkTest.api.request.SensorApiRequest;
import com.example.oneworkTest.station.entity.Sensor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VoltMapper {
    VoltMapper INSTANCE = Mappers.getMapper(VoltMapper.class);

    Sensor fromRequest(SensorApiRequest request);
}
