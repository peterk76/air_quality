package com.air.quality.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "Note")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Note implements Serializable {

    @Serial
    private static final long serialVersionUID = 6592780945309659374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "note_id_seq")
    private Integer id;

    UUID uuid;
    UUID cityId;
    String topic;
    LocalDateTime dateAdd;
    @Setter
    LocalDateTime dateMod;
    String userMod;
    @Setter
    String text;

    private Note(final UUID uuid, final UUID cityId, final String topic, final LocalDateTime dateAdd, final LocalDateTime dateMod, final String userMod, final String text) {
        this.uuid = uuid;
        this.cityId = cityId;
        this.topic = topic;
        this.dateAdd = dateAdd;
        this.dateMod = dateMod;
        this.userMod = userMod;
        this.text = text;
    }

    public static Note of(final UUID uuid, final UUID cityId, final String topic, final LocalDateTime dateAdd, final LocalDateTime dateMod, final String userMod, final String text) {
        return new Note(uuid, cityId, topic, dateAdd, dateMod, userMod, text);
    }
}
