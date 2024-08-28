package com.example.demo.building.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
import java.util.List;

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

//    @Schema(description = "RAW")
//    @JoinColumn(name = "RAW")

    @OneToOne(mappedBy = "sensor")
//    @JoinColumn(name = "raw_id")
//    @JsonManagedReference
    private Raw raw;

//    @Schema(description = "電壓資訊")
//    @OneToOne(mappedBy = "sensor", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    private Volt volt;
//
//    @Schema(description = "溫溼度資訊")
//    @OneToOne(mappedBy = "sensor", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    private StickTxRh stickTxRh;
//
//    @Schema(description = "超音波資訊")
//    @OneToOne(mappedBy = "sensor", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    private UltrasonicLevel ultrasonicLevel;
//
//    @Schema(description = "水流資訊")
//    @OneToOne(mappedBy = "sensor", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//    private WaterSpeedAquark waterSpeedAquark;


    @Schema(description = "電壓資訊")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "volt_id", referencedColumnName = "id")
    private Volt volt;

    @Schema(description = "溫溼度資訊")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stick_tx_rh_id", referencedColumnName = "id")
    private StickTxRh stickTxRh;

    @Schema(description = "超音波資訊")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ultrasonic_level_id", referencedColumnName = "id")
    private UltrasonicLevel ultrasonicLevel;

    @Schema(description = "水流資訊")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "water_speed_aquark_id", referencedColumnName = "id")
    private WaterSpeedAquark waterSpeedAquark;

//    @Schema(description = "RAW")
//    @OneToOne(mappedBy = "sensor", orphanRemoval = true)
//    @JoinTable(name = "RAW_SENSOR",
//            joinColumns = @JoinColumn(name = "sensor_ID"),
//            inverseJoinColumns = @JoinColumn(name = "raw_ID"))
//    private Raw raw;

}
