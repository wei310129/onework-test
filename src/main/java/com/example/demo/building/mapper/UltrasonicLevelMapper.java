package com.example.demo.building.mapper;

import com.example.demo.building.entity.UltrasonicLevel;
import com.example.demo.building.response.UltrasonicLevelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UltrasonicLevelMapper {
    UltrasonicLevelMapper INSTANCE = Mappers.getMapper(UltrasonicLevelMapper.class);

    UltrasonicLevel fromRequest(UltrasonicLevelResponse request);
}
