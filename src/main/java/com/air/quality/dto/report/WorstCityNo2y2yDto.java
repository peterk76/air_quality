package com.air.quality.dto.report;

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
public class WorstCityNo2y2yDto implements Serializable {

    @Serial
    static final long serialVersionUID = 8725612693822036858L;

    String city;
    String cityId;
    String country;
    String avgNo2Current;
    String avgNo2YearBefore;

    public static WorstCityNo2y2yDto of(
            final String city,
            final String cityId,
            final String country,
            final String avgNo2Current,
            final String avgNo2YearBefore) {
        return new WorstCityNo2y2yDto(city, cityId, country, avgNo2Current, avgNo2YearBefore);
    }
}
