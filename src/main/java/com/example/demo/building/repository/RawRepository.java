package com.example.demo.building.repository;

import com.example.demo.building.entity.Raw;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ApiOperation(value = "取得用戶資訊")
public interface RawRepository extends JpaSpecificationExecutor<Raw>, JpaRepository<Raw, Long> {
    @Query("SELECT r FROM Raw r LEFT JOIN FETCH r.sensor s LEFT JOIN FETCH s.volt LEFT JOIN FETCH s.stickTxRh LEFT JOIN FETCH s.ultrasonicLevel LEFT JOIN FETCH s.waterSpeedAquark")
    List<Raw> findAllWithAllRelations();
}
