package com.example.demo.building.schedule;

import com.example.demo.building.api.BuildingApiService;
import com.example.demo.building.response.RawResponse;
import com.example.demo.building.service.BuildingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class BuildingMonitorTask {

    private final BuildingApiService buildingApiService;

    private final BuildingService buildingService;

    // 每天上午10:00執行
    @Scheduled(cron = "0 0 10 * * ?")
    public void buildingMonitorJob() throws Exception {
        LocalDateTime yesterday = LocalDate.now().atStartOfDay().minusDays(1L);
        final List<RawResponse> buildingMonitorRequests = buildingApiService.getExternalApiData();
        buildingService.insert(buildingMonitorRequests.stream()
                .filter(monitor -> monitor.getObsDateTime() != null)
                .filter(monitor -> !monitor.getObsDateTime().isBefore(yesterday)).collect(Collectors.toList()));
    }
}
