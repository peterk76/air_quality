package com.air.quality.dto.city_notes;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public final class NoteAddDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2946202693822018458L;

    String topic;
    String user;    // TODO może jakoś z kontekstu?
    String note;
}
