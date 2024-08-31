package com.example.oneworkTest.station.schedule;

import com.example.oneworkTest.api.service.BuildingApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RawTask {

    private final BuildingApiService buildingApiService;

    /**
     * 儲存每日資料到SQL（每天凌晨01:00執行）
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void buildingMonitorJob() {
        buildingApiService.getAllExternalApiDataBlocking();
    }

}
