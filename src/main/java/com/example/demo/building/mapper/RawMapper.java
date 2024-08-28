package com.example.demo.building.mapper;

import com.example.demo.building.entity.Raw;
import com.example.demo.building.response.RawResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.DayOfWeek;

@Mapper(componentModel = "spring", uses = {SensorMapper.class})
public interface RawMapper {
    RawMapper INSTANCE = Mappers.getMapper(RawMapper.class);

    @Mapping(source = "sensor", target = "sensor")
    @Mapping(source = "dayOfWeek", target = "dayOfWeek")
    Raw fromRequest(RawResponse request);

    RawResponse toRequest(Raw request);

//    default DayOfWeek mapDayOfWeek(DayOfWeek dayOfWeek) {
//        if (dayOfWeek == null) {
//            return null;
//        }
//        return dayOfWeek;
//    }
}
