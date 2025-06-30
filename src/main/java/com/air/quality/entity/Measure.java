package com.air.quality.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "Measure")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Measure implements Serializable {

    @Serial
    private static final long serialVersionUID = 6593272045309659374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "measure_id_seq")
    private Integer id;

    UUID sensorId;
    UUID cityId;
    float pm10;
    float co;
    float no2;
    LocalDateTime timestamp;

    public Measure(final UUID sensorId, final UUID cityId, final float pm10, final float co, final float no2, final LocalDateTime timestamp) {
        this.sensorId = sensorId;
        this.cityId = cityId;
        this.pm10 = pm10;
        this.co = co;
        this.no2 = no2;
        this.timestamp = timestamp;
    }

    public static Measure of(final UUID sensorId, final UUID cityId, final float pm10, final float co, final float no2, final LocalDateTime timestamp) {
        return new Measure(sensorId, cityId, pm10, co, no2, timestamp);
    }
}
