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
@Schema(description = "超音波資訊")
@Table(name = "ULTRASONIC_LEVEL")
public class UltrasonicLevel implements Serializable {
    @Schema(description = "ID")
    @Id
    @SequenceGenerator(name = "sequenceUltrasonicLevel", sequenceName = "SEQ_ULTRASONIC_LEVEL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceUltrasonicLevel")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Schema(description = "SENSOR")
    @JsonBackReference
    @OneToOne(mappedBy = "ultrasonicLevel")
    private Sensor sensor;

    @Schema(description = "ECHO")
    @Column(name = "ECHO")
    private Double echo;

}
