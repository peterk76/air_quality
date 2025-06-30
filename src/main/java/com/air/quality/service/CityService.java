package com.air.quality.service;

import com.air.quality.model.City;
import com.air.quality.utils.Json;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class CityService {

    public List<City> getCitiesByRegionId(final UUID regionId) {
        return getAllCities().stream()
                .filter(city -> city.getRegionId().equals(regionId))
                .toList();
    }

    @SneakyThrows
    public List<City> getAllCities() {
        var url = new URL("https://api.europeancitiesdictionary.info/cities");
        var con = getConnection(url);
        var citiesRaw = getResponse(con, Cities.class);
        return citiesRaw.stream()
                .flatMap(cityRaw -> cityRaw.getCities().stream())
                .map(cityRaw -> City.of(
                        cityRaw.getCountry(),
                        cityRaw.getCity(),
                        UUID.fromString(cityRaw.getCityId()),
                        cityRaw.getRegion(),
                        UUID.fromString(cityRaw.getRegionId())))
                .toList();
    }

    @SneakyThrows
    public Optional<City> getCityById(final UUID cityId) {
        var url = new URL("https://api.europeancitiesdictionary.info/cities/{" + cityId.toString() + "}");
        var con = getConnection(url);
        var cityRawOpt = getResponse(con, CityNoId.class);
        return cityRawOpt
                .map(cityRaw -> City.of(
                        cityRaw.getCountry(),
                        cityRaw.getCity(),
                        cityId,
                        cityRaw.getRegion(),
                        UUID.fromString(cityRaw.getRegionId())));
    }

    @SneakyThrows
    private HttpURLConnection getConnection(final URL url) {
        var con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return con;
    }

    @SneakyThrows
    private <T> Optional<T> getResponse(final HttpURLConnection con, final Class<T> clazz) {
        StringBuilder resp = new StringBuilder();
        var in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            resp.append(inputLine);
        }
        in.close();
        return Json.getObjectFromJson(resp.toString(), clazz);
    }

    @Getter
    private static final class Cities {
        String lastUpdate;
        List<CityWithId> cities;
    }

    @Getter
    private static final class CityNoId {
        String country;
        String city;
        String region;
        String regionId;
    }

    @Getter
    private static final class CityWithId {
        String country;
        String city;
        String cityId;
        String region;
        String regionId;
    }
}
