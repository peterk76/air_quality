package com.air.quality.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@AllArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class City {

    String country;
    String city;
    UUID cityId;
    String region;
    UUID regionId;

}
