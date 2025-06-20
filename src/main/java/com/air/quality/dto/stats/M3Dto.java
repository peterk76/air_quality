package com.air.quality.dto.stats;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class M3Dto {

    List<String> risingCO3MCities;
    List<String> risingPM103MCities;

    public static M3Dto of(final List<String> risingCO3MCities, final List<String> risingPM103MCities) {
        return new M3Dto(risingCO3MCities,  risingPM103MCities);
    }
}
