package com.example.demo.building.repository;

import com.example.demo.building.entity.UltrasonicLevel;
import com.example.demo.building.entity.Volt;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@ApiOperation(value = "取得用戶資訊")
public interface UltrasonicLevelRepository extends JpaSpecificationExecutor<UltrasonicLevel>, JpaRepository<UltrasonicLevel, Long> {

}
