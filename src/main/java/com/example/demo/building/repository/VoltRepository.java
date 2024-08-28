package com.example.demo.building.repository;

import com.example.demo.building.entity.Sensor;
import com.example.demo.building.entity.Volt;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@ApiOperation(value = "取得用戶資訊")
public interface VoltRepository extends JpaSpecificationExecutor<Volt>, JpaRepository<Volt, Long> {

}
