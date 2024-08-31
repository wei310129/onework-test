package com.example.oneworkTest.station.mapper;

import com.example.oneworkTest.api.request.UltrasonicLevelApiRequest;
import com.example.oneworkTest.station.entity.UltrasonicLevel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UltrasonicLevelMapper {
    UltrasonicLevelMapper INSTANCE = Mappers.getMapper(UltrasonicLevelMapper.class);

    UltrasonicLevel fromRequest(UltrasonicLevelApiRequest request);
}
