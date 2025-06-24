package com.air.quality.service;

import com.air.quality.model.City;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class CityService {

    List<City> cities = new ArrayList<>();

    @EventListener(ApplicationReadyEvent.class)
    private void init() {
        IntStream.rangeClosed(1, 200)
                .forEach(city ->
                        cities.add(City.of("Poland", String.format("%03d", city), UUID.fromString("00000000-0000-0000-0000-000000000" + String.format("%03d", city)), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22")))
                );
    }

    public List<City> getCitiesByRegionId(final UUID regionId) {
        return getAllCities().stream()
                .filter(city -> city.getRegionId().equals(regionId))
                .toList();
    }

    public List<City> getAllCities() { // TODO implement fake URL ?
        return cities;
    }

    public Optional<City> getCityById(final UUID cityId) { // TODO wywalić ?
        return cities.stream()
                .filter(city -> city.getCityId().equals(cityId))
                .findFirst();
    }
}
