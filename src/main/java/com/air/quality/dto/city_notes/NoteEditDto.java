package com.air.quality.dto.city_notes;

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
public final class NoteEditDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 8703912693822018458L;

    UUID uuid;
    String text;
}
