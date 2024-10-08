package com.example.oneworkTest.station.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;


@Getter
@Setter
@Entity
@Schema(description = "水流資訊")
@Table(name = "WATER_SPEED_AQUARK")
public class WaterSpeedAquark implements Serializable {
    @Schema(description = "ID")
    @Id
    @SequenceGenerator(name = "sequenceWaterSpeedAquark", sequenceName = "SEQ_WATER_SPEED_AQUARK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceWaterSpeedAquark")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Schema(description = "SENSOR")
    @JsonBackReference
    @OneToOne(mappedBy = "waterSpeedAquark")
    private Sensor sensor;

    @Schema(description = "表面流速m/s")
    @Column(name = "SPEED")
    private Double speed;
}
