package com.example.oneworkTest.station.mapper;

import com.example.oneworkTest.api.request.StickTxRhApiRequest;
import com.example.oneworkTest.station.entity.StickTxRh;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StickTxRhMapper {
    StickTxRhMapper INSTANCE = Mappers.getMapper(StickTxRhMapper.class);

    StickTxRh fromRequest(StickTxRhApiRequest request);
}
