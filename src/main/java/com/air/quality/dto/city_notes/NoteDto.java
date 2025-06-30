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
public final class NoteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6800612693822017498L;

    UUID uuid;
    String topic;
    String dateAdd;
    String dateMod;
    String user;
    String text;

    public static NoteDto of(final UUID uuid, final String topic, final String dateAdd, final String dateMod, final String user, final String text) {
        return new NoteDto(uuid, topic, dateAdd, dateMod, user, text);
    }
}
