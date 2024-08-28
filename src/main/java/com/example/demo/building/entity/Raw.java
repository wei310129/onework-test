package com.example.demo.building.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import io.swagger.v3.oas.annotations.media.Schema;


import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@Entity
@Schema(description = "建築監控資訊")
@Table(name = "RAW")
public class Raw implements Serializable {
    @Schema(description = "ID")
    @Id
    @SequenceGenerator(name = "sequenceRaw", sequenceName = "SEQ_RAW")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceRaw")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Schema(description = "站點ID")
    @Column(name = "STATION_ID")
    private Long stationId;

    @Schema(description = "觀測日期時間")
    @Column(name = "OBS_DATE_TIME")
    private LocalDateTime obsDateTime;

    @Schema(description = "觀測時間")
    @Column(name = "OBS_TIME")
    private LocalTime obsTime;

    @Schema(description = "CSQ")
    @Column(name = "CSQ")
    private Long csq;

    @Schema(description = "感測器")
//    @OneToOne(mappedBy = "raw", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    @OneToOne
//    @JoinColumn(name = "sensor_id")
//    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
//    @JsonBackReference
    private Sensor sensor;

    @Schema(description = "日累積雨量mm")
    @Column(name = "RAIN_D")
    private Long rainD;

    @Schema(description = "星期")
    @Enumerated(EnumType.STRING)
    @Column(name = "DAY_OF_WEEK")
    private DayOfWeek dayOfWeek;
}
