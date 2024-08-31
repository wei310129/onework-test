package com.example.oneworkTest.station.mapper;

import com.example.oneworkTest.station.entity.Raw;
import com.example.oneworkTest.api.request.RawApiRequest;
import com.example.oneworkTest.api.response.RawApiResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper(componentModel = "spring", uses = {SensorMapper.class})
public interface RawMapper {
    RawMapper INSTANCE = Mappers.getMapper(RawMapper.class);

    @Mapping(source = "sensor", target = "sensor")
    @Mapping(source = "dayOfWeek", target = "dayOfWeek")
    Raw fromApiRequest(RawApiRequest request);

    @Mapping(source = "obsDateTime", target = "obsDate", qualifiedByName = "mapToLocalDate")
    @Mapping(source = "obsDateTime", target = "obsTime", qualifiedByName = "mapToLocalTime")
    RawApiResponse toApiResponse(Raw raw);

    RawApiResponse fromReqToRes(RawApiRequest raw);

    // 自定義方法將 LocalDateTime 轉換為 LocalDate
    @Named("mapToLocalDate")
    static LocalDate mapToLocalDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate() : null;
    }

    // 自定義方法將 LocalDateTime 轉換為 LocalTime
    @Named("mapToLocalTime")
    static LocalTime mapToLocalTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalTime() : null;
    }
}
