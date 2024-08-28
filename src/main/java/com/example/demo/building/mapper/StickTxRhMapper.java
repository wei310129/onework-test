package com.example.demo.building.mapper;

import com.example.demo.building.entity.StickTxRh;
import com.example.demo.building.response.StickTxRhResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StickTxRhMapper {
    StickTxRhMapper INSTANCE = Mappers.getMapper(StickTxRhMapper.class);

    StickTxRh fromRequest(StickTxRhResponse request);
}
