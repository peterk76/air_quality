package com.air.quality.dto.measure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public final class MeasureDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7390612693822018458L;

    UUID sensorId;
    UUID cityId;
    @JsonProperty("PM10")
    float pm10;
    @JsonProperty("CO")
    float co;
    @JsonProperty("NO2")
    float no2;
    long timestamp;
}
