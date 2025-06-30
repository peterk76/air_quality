package com.air.quality.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Json {

    static final String PARSE_ERROR = "Parse error [{}]";

    public static <T> Optional<T> getObjectFromJson(final String value, final Class<T> clazz) {
        T object;
        var gson = new Gson();
        try {
            object = gson.fromJson(value, clazz);
        } catch (Exception e) {
            log.warn(PARSE_ERROR, value, e);
            return Optional.empty();
        }
        if (object == null) {
            log.warn(PARSE_ERROR, value);
            return Optional.empty();
        }
        return Optional.of(object);
    }
}
