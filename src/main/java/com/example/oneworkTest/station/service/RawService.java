package com.example.oneworkTest.station.service;

import com.example.oneworkTest.api.request.RawApiRequest;
import com.example.oneworkTest.api.response.*;
import com.example.oneworkTest.cache.redis.RawRedisSpecification;
import com.example.oneworkTest.cache.redis.RedisConfig;
import com.example.oneworkTest.station.entity.Raw;
import com.example.oneworkTest.station.mapper.RawMapper;
import com.example.oneworkTest.station.repository.*;
import com.example.oneworkTest.station.request.RawRequest;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import net.kaczmarzyk.spring.data.jpa.web.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RawService {
    private final RawRepository rawRepository;

    private final RawMapper rawMapper;

    @Cacheable(value = RedisConfig.RAW, keyGenerator = "keyGenerator")
    public List<Raw> query(RawRequest request) {
        return rawRepository.findAll(this.convertToSpec(request));
    }

    @Cacheable(value = RedisConfig.RAW)
    public List<Raw> queryAll() {
        return rawRepository.findAll();
    }

    @CacheEvict(value = RedisConfig.RAW, allEntries = true)
    public List<Raw> insert(List<RawApiRequest> buildingMonitorRequests) {
        List<Raw> forms = buildingMonitorRequests.stream()
                .map(rawMapper::fromApiRequest)
                .peek(raw -> raw.getSensor().setRaw(raw)) // 建立互相關聯
                .peek(raw -> raw.setDayOfWeek(raw.getObsDateTime().getDayOfWeek()))
                .peek(raw -> raw.setObsTime(raw.getObsDateTime().toLocalTime()))
                .collect(Collectors.toList());
        List<Raw> raws = rawRepository.saveAll(forms);
        return raws;
    }

    public RawRedisSpecification<Raw> convertToSpec(RawRequest request) {
        return ((root, cq, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (request.getId() != null) {
                predicates.add(cb.equal(root.get("id"), request.getId()));
            }
            if (request.getStationId() != null) {
                predicates.add(cb.equal(root.get("stationId"), request.getStationId()));
            }
            if (request.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("obsDateTime"), request.getStartDate()));
            }
            if (request.getEndDate() != null) {
                predicates.add(cb.lessThan(root.get("obsDateTime"), request.getEndDate()));
            }
            if (request.getDayOfWeek() != null) {
                predicates.add(cb.equal(root.get("dayOfWeek"), request.getDayOfWeek()));
            }

            if (!predicates.isEmpty()) {
                cq.where(cb.and(predicates.toArray(new Predicate[]{})));
            }
            return cq.getRestriction();  // 返回查詢的限制條件
        });
    }

    public boolean isInDayRange(DayOfWeek startDay, DayOfWeek endDay, DayOfWeek targetDay) {
        if (startDay == endDay && startDay == targetDay) {
            return true;
        } else if (endDay.compareTo(startDay) > 0 && endDay.compareTo(targetDay) >= 0 && targetDay.compareTo(startDay) >= 0) {
            return true;
        } else if (startDay.compareTo(endDay) > 0 && (targetDay.compareTo(startDay) >= 0 || endDay.compareTo(targetDay) >= 0)) {
            return true;
        }
        return false;
    }

    public boolean isInTimeRange(LocalTime startTime, LocalTime endTime, LocalTime targetTime) {
        return !endTime.isBefore(startTime) && !targetTime.isBefore(startTime) && !targetTime.isAfter(endTime);
    }

    public RawApiResponse countRawAverage(List<RawApiResponse> rawApiResponses) {
        final RawApiResponse rawApiResponse = new RawApiResponse();
        if (rawApiResponses.isEmpty()) {
            return rawApiResponse;
        }
        int amount = rawApiResponses.size();
        final List<Double> rainList = new ArrayList<>();
        final List<VoltApiResponse> volts = new ArrayList<>();
        final List<StickTxRhApiResponse> stickTxRhs = new ArrayList<>();
        final List<UltrasonicLevelApiResponse> ultrasonicLevels = new ArrayList<>();
        final List<WaterSpeedAquarkApiResponse> waterSpeedAquarks = new ArrayList<>();
        rawApiResponses.forEach(res -> {
            rainList.add(res.getRainD());
            volts.add(res.getSensor().getVolt());
            stickTxRhs.add(res.getSensor().getStickTxRh());
            ultrasonicLevels.add(res.getSensor().getUltrasonicLevel());
            waterSpeedAquarks.add(res.getSensor().getWaterSpeedAquark());
        });
        rawApiResponse.setRainD(rainList.stream().reduce(0.0, Double::sum) / amount);
        final LocalDateTime obsDateTime = rawApiResponses.stream().filter(Objects::nonNull).findAny().map(RawApiResponse::getObsDateTime).orElse(null);
        rawApiResponse.setObsDateTime(obsDateTime);
        final int roundAmount = 2;
        final SensorApiResponse sensorApiResponse = this.countSensorAverage(obsDateTime, roundAmount, volts, stickTxRhs, ultrasonicLevels, waterSpeedAquarks);
        rawApiResponse.setSensor(sensorApiResponse);

        return rawApiResponse;
    }

    public SensorApiResponse countSensorAverage(final LocalDateTime obsDateTime, final int roundAmount, final List<VoltApiResponse> volts, final List<StickTxRhApiResponse> stickTxRhs, final List<UltrasonicLevelApiResponse> ultrasonicLevels, final List<WaterSpeedAquarkApiResponse> waterSpeedAquarks) {
        final SensorApiResponse newSensorApiResponse = new SensorApiResponse();
        int amount = volts.size();
        // volt
        final VoltApiResponse newVolt = new VoltApiResponse();
        Double v1 = Double.valueOf("0");
        Double v2 = Double.valueOf("0");
        Double v3 = Double.valueOf("0");
        Double v4 = Double.valueOf("0");
        Double v5 = Double.valueOf("0");
        Double v6 = Double.valueOf("0");
        Double v7 = Double.valueOf("0");
        for (int i = 0; i < volts.size(); i++) {
            if (volts.get(i) == null) {
                this.logDebugSetter(obsDateTime, "volts", i);
                continue;
            }
            v1 += volts.get(i).getV1();
            v2 += volts.get(i).getV2();
            v3 += volts.get(i).getV3();
            v4 += volts.get(i).getV4();
            v5 += volts.get(i).getV5();
            v6 += volts.get(i).getV6();
            v7 += volts.get(i).getV7();
        }
        newVolt.setV1(mathRound(v1 / amount, roundAmount));
        newVolt.setV2(mathRound(v2 / amount, roundAmount));
        newVolt.setV3(mathRound(v3 / amount, roundAmount));
        newVolt.setV4(mathRound(v4 / amount, roundAmount));
        newVolt.setV5(mathRound(v5 / amount, roundAmount));
        newVolt.setV6(mathRound(v6 / amount, roundAmount));
        newVolt.setV7(mathRound(v7 / amount, roundAmount));
        newSensorApiResponse.setVolt(newVolt);

        // StickTxRh
        final StickTxRhApiResponse newStickTxRhApiResponse = new StickTxRhApiResponse();
        Double tx = Double.valueOf("0");
        Double rh = Double.valueOf("0");
        for (int i = 0; i < stickTxRhs.size(); i++) {
            if (stickTxRhs.get(i) == null) {
                this.logDebugSetter(obsDateTime, "stickTxRhs", i);
                continue;
            }
            tx += stickTxRhs.get(i).getTx();
            rh += stickTxRhs.get(i).getRh();
        }
        newStickTxRhApiResponse.setTx(this.mathRound(tx / amount, roundAmount));
        newStickTxRhApiResponse.setRh(this.mathRound(rh / amount, roundAmount));
        newSensorApiResponse.setStickTxRh(newStickTxRhApiResponse);

        // UltrasonicLevel
        final UltrasonicLevelApiResponse newUltrasonicLevelApiResponse = new UltrasonicLevelApiResponse();
        Double echo = Double.valueOf("0");
        for (int i = 0; i < ultrasonicLevels.size(); i++) {
            if (ultrasonicLevels.get(i) == null) {
                this.logDebugSetter(obsDateTime, "ultrasonicLevels", i);
                continue;
            }
            echo += ultrasonicLevels.get(i).getEcho();
        }
        newUltrasonicLevelApiResponse.setEcho(this.mathRound(echo / amount, roundAmount));
        newSensorApiResponse.setUltrasonicLevel(newUltrasonicLevelApiResponse);

        // WaterSpeedAquark
        final WaterSpeedAquarkApiResponse newWaterSpeedAquarkApiResponse = new WaterSpeedAquarkApiResponse();
        Double speed = Double.valueOf("0");
        for (int i = 0; i < waterSpeedAquarks.size(); i++) {
            if (waterSpeedAquarks.get(i) == null) {
                this.logDebugSetter(obsDateTime, "waterSpeedAquarks", i);
                continue;
            }
            speed += waterSpeedAquarks.get(i).getSpeed();
        }
        newWaterSpeedAquarkApiResponse.setSpeed(this.mathRound(speed / amount, roundAmount));
        newSensorApiResponse.setWaterSpeedAquark(newWaterSpeedAquarkApiResponse);

        return newSensorApiResponse;
    }

    private void logDebugSetter(final LocalDateTime obsDateTime, final String param, final int index) {
        log.debug("日期:{} 屬性:{} 序號:{} 有空值", obsDateTime, param, index);
    }

    private double mathRound(double input, int roundAmount) {
        double adjust = Math.pow(10, roundAmount);
        return Math.round(input * adjust) / adjust;
    }
}
