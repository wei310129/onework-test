package com.example.demo.building.mapper;

import com.example.demo.building.entity.Sensor;
import com.example.demo.building.response.SensorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VoltMapper {
    VoltMapper INSTANCE = Mappers.getMapper(VoltMapper.class);

    Sensor fromRequest(SensorResponse request);
}
