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
public final class NoteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6800612693822017498L;

    String id; // TODO UUID ?
    String topic;
    String dateAdd;
    String dateMod;
    String user;
    String note;

    public static NoteDto of(final String id, final String topic, final String dateAdd, final String dateMod, final String user, final String note) {
        return new NoteDto(id, topic, dateAdd, dateMod, user, note);
    }
}
