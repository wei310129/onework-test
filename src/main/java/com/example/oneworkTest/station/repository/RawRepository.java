package com.example.oneworkTest.station.repository;

import com.example.oneworkTest.station.entity.Raw;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ApiOperation(value = "阻塞式取得用戶資訊")
public interface RawRepository extends JpaSpecificationExecutor<Raw>, JpaRepository<Raw, Long> {
}
