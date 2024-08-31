package com.example.oneworkTest.station.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Schema(description = "電壓資訊")
@Table(name = "VOLT")
public class Volt implements Serializable {
    @Schema(description = "ID")
    @Id
    @SequenceGenerator(name = "sequenceVolt", sequenceName = "SEQ_VOLT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceVolt")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Schema(description = "SENSOR")
    @JsonBackReference
    @OneToOne(mappedBy = "volt")
    private Sensor sensor;

    @Schema(description = "V1")
    @Column(name = "V1")
    private Double v1;

    @Schema(description = "V2")
    @Column(name = "V2")
    private Double v2;

    @Schema(description = "V3")
    @Column(name = "V3")
    private Double v3;

    @Schema(description = "V4")
    @Column(name = "V4")
    private Double v4;

    @Schema(description = "太陽能板1電壓 V")
    @Column(name = "V5")
    private Double v5;

    @Schema(description = "太陽能板1電壓 V")
    @Column(name = "V6")
    private Double v6;

    @Schema(description = "日累積雨量mm")
    @Column(name = "V7")
    private Double v7;

}
