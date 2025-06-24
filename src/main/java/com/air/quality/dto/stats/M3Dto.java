package com.air.quality.dto.stats;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuppressWarnings({"java:S1948"}) // Sonar doesn't recognize Lombok's FieldDefaults
public final class M3Dto implements Serializable {

    @Serial
    static final long serialVersionUID = 7209612693822018458L;

    List<String> risingCO3MCities;
    List<String> risingPM103MCities;

    public static M3Dto of(final List<String> risingCO3MCities, final List<String> risingPM103MCities) {
        return new M3Dto(risingCO3MCities,  risingPM103MCities);
    }
}
