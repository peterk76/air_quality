package com.air.quality.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Immutable;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Entity
@Immutable
@Table(name = "average")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Average implements Serializable {

    @Serial
    private static final long serialVersionUID = 2367772045309659374L;

    @Id
    private Long id;

    String yrmon;
    UUID cit;
    Float avCo;
    Float avPm10;
}
