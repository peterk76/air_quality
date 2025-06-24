package com.air.quality.service;

import com.air.quality.model.City;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class CityService {

    final List<City> cities = List.of(
            City.of("Poland", "Katowice", UUID.fromString("a0c492f2-8b96-1e7f-8a7a-286866d3fa71"), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22")),
            City.of("Poland", "Gliwice", UUID.fromString("a0c492f2-8b96-1e7f-8a7a-286866d3fa72"), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22")),
            City.of("Poland", "Zabrze", UUID.fromString("a0c492f2-8b96-1e7f-8a7a-286866d3fa73"), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22")),
            City.of("Poland", "Mikołów", UUID.fromString("a0c492f2-8b96-1e7f-8a7a-286866d3fa74"), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22")),
            City.of("Poland", "Ruda Śląska", UUID.fromString("a0c492f2-8b96-1e7f-8a7a-286866d3fa75"), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22")),
            City.of("Poland", "Monachium", UUID.fromString("a0c492f2-8b96-1e7f-8a7a-286866d3fa76"), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22")),
            City.of("Poland", "Praga", UUID.fromString("a0c492f2-8b96-1e7f-8a7a-286866d3fa77"), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22")),
            City.of("Poland", "Poznań", UUID.fromString("a0c492f2-8b96-1e7f-8a7a-286866d3fa78"), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22")),
            City.of("Poland", "Gdańsk", UUID.fromString("00000000-0000-0000-0000-000000000001"), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22")),
            City.of("Poland", "Gdynia", UUID.fromString("00000000-0000-0000-0000-000000000002"), "śląskie", UUID.fromString("a04295f2-8d96-4a7d-8a7a-286866d3fa22"))
    );

    public List<City> getCitiesByRegionId(final UUID regionId) {
        return getAllCities().stream()
                .filter(city -> city.getRegionId().equals(regionId))
                .toList();
    }

    private List<City> getAllCities() { // TODO implement fake URL ?
        return cities;
    }

    public Optional<City> getCityById(final UUID cityId) { // TODO wywalić ?
        return cities.stream()
                .filter(city -> city.getCityId().equals(cityId))
                .findFirst();
    }
}
