package com.example.demo.building.service;

import com.example.demo.building.entity.Raw;
import com.example.demo.building.entity.Sensor;
import com.example.demo.building.mapper.RawMapper;
import com.example.demo.building.repository.*;
import com.example.demo.building.request.BuildingMonitorRequest;
import com.example.demo.building.response.RawResponse;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import net.kaczmarzyk.spring.data.jpa.web.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final RawRepository rawRepository;

    private final SensorRepository sensorRepository;

    private final StickTxRhRepository stickTxRhRepository;

    private final UltrasonicLevelRepository ultrasonicLevelRepository;

    private final VoltRepository voltRepository;

    private final WaterSpeedAquarkRepository waterSpeedAquarkRepository;

    @Autowired
    private RawMapper rawMapper;

//    public Specification<Raw> buildSpec(BuildingMonitorRequest request) {
//        return (root, cq, cb) -> {
//            // 查詢邏輯
//        };
//    }

    public Page<Raw> query(
            @And({
                    @Spec(path = "id", spec = Equal.class),
                    @Spec(path = "stationId", spec = Equal.class),
                    @Spec(path = "csq", spec = Equal.class),
                    @Spec(path = "rainD", spec = Equal.class)
            }) Specification<Raw> request, Pageable pageable) {
        return rawRepository.findAll(request, pageable);
    }

    public List<Raw> queryAll() {
        List<Raw> raws = rawRepository.findAll();
//        raws.stream().map(Raw::getSensor).forEach(sensor ->{
//            sensor.getVolt();
//            sensor.getStickTxRh();
//            sensor.getUltrasonicLevel();
//            sensor.getWaterSpeedAquark();
//        });
        return raws;
    }

    public Raw queryReferenceById(Long id) {
        return rawRepository.getReferenceById(id);
    }

    public List<Raw> insert(List<RawResponse> buildingMonitorRequests) {
        List<Raw> forms = buildingMonitorRequests.stream()
                .map(rawMapper::fromRequest)
                .peek(raw -> raw.getSensor().setRaw(raw)) // 建立互相關聯
                .peek(raw -> raw.setDayOfWeek(raw.getObsDateTime().getDayOfWeek()))
                .peek(raw -> raw.setObsTime(raw.getObsDateTime().toLocalTime()))
                .collect(Collectors.toList());
        List<Raw> raws = rawRepository.saveAll(forms);
        return raws;
    }
}
