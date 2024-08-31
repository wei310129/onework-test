package com.example.oneworkTest.station.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;


import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;


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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "OBS_DATE_TIME")
    private LocalDateTime obsDateTime;

    @Schema(description = "觀測時間")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    @Column(name = "OBS_TIME")
    private LocalTime obsTime;

    @Schema(description = "CSQ")
    @Column(name = "CSQ")
    private Long csq;

    @Schema(description = "感測器")
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @Schema(description = "日累積雨量mm")
    @Column(name = "RAIN_D")
    private Long rainD;

    @Schema(description = "星期")
    @Enumerated(EnumType.STRING)
    @Column(name = "DAY_OF_WEEK")
    private DayOfWeek dayOfWeek;
}
