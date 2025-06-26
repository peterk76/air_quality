package com.air.quality.dto.stats;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuppressWarnings({"java:S1948"}) // Sonar doesn't recognize Lombok's FieldDefaults
public final class H1CityDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1864812693822018458L;

    String avgN02LastHour;
    String maxNO2LastHour;
    String minNO2LastHour;
    String avgCOLastHour;
    String maxCOLastHour;
    String minCOLastHour;
    String avgPM10LastHour;
    String maxPM10LastHour;
    String minPM10LastHour;

    @SuppressWarnings({"java:S107"})
    public static H1CityDto of(
        final String avgN02LastHour,
        final String maxNO2LastHour,
        final String minNO2LastHour,
        final String avgCOLastHour,
        final String maxCOLastHour,
        final String minCOLastHour,
        final String avgPM10LastHour,
        final String maxPM10LastHour,
        final String minPM10LastHour) {
        return new H1CityDto(avgN02LastHour, maxNO2LastHour, minNO2LastHour,
                avgCOLastHour, maxCOLastHour, minCOLastHour,
                avgPM10LastHour, maxPM10LastHour, minPM10LastHour);
    }
}
