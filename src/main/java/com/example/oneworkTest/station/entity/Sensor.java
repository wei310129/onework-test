package com.example.oneworkTest.station.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Schema(description = "感測器資訊")
@Table(name = "SENSOR")
public class Sensor implements Serializable {
    @Schema(description = "ID")
    @Id
    @SequenceGenerator(name = "sequenceSensor", sequenceName = "SEQ_SENSOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceSensor")
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "sensor")
    @JsonBackReference
    private Raw raw;

    @Schema(description = "電壓資訊")
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "volt_id", referencedColumnName = "id")
    private Volt volt;

    @Schema(description = "溫溼度資訊")
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stick_tx_rh_id", referencedColumnName = "id")
    private StickTxRh stickTxRh;

    @Schema(description = "超音波資訊")
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ultrasonic_level_id", referencedColumnName = "id")
    private UltrasonicLevel ultrasonicLevel;

    @Schema(description = "水流資訊")
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "water_speed_aquark_id", referencedColumnName = "id")
    private WaterSpeedAquark waterSpeedAquark;

}
