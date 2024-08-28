package com.example.demo.building.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Schema(description = "溫溼度資訊")
@Table(name = "STRICK_TX_RH")
public class StickTxRh implements Serializable {
    @Schema(description = "ID")
    @Id
    @SequenceGenerator(name = "sequenceStickTxRh", sequenceName = "SEQ_STRICK_TX_RH")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceStickTxRh")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Schema(description = "SENSOR")
    @OneToOne(mappedBy = "stickTxRh")
//    @JoinColumn(name = "SENSOR")
    private Sensor sensor;

    @Schema(description = "濕度%")
    @Column(name = "RH")
    private Double rh;

    @Schema(description = "溫度℃")
    @Column(name = "TX")
    private Double tx;

}
