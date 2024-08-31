package com.example.oneworkTest.station.repository;

import com.example.oneworkTest.station.entity.Sensor;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@ApiOperation(value = "取得用戶資訊")
public interface SensorRepository extends JpaSpecificationExecutor<Sensor>, JpaRepository<Sensor, Long> {

}
