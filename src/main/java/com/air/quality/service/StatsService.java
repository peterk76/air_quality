package com.air.quality.service;

import com.air.quality.dto.stats.H1CityDto;
import com.air.quality.dto.stats.M3Dto;
import com.air.quality.entity.AverageCP;
import com.air.quality.entity.Measure;
import com.air.quality.model.City;
import com.air.quality.repository.AverageCpRepositoryImpl;
import com.air.quality.repository.MeasureRepositoryImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class StatsService {

    static final int FULL_MONTH_COUNT = 3;

    final MeasureRepositoryImpl measureRepository;
    final CityService cityService;
    final AverageCpRepositoryImpl averageCpRepository;

    public M3Dto get3M(final UUID regionId) {
        var cities = cityService.getCitiesByRegionId(regionId);
        if (cities.isEmpty()) {
            return M3Dto.of(List.of(), List.of());
        }
        var citiesUids = cities.stream()
                .map(City::getCityId)
                .toList();
        var dates = List.of(getDate(1), getDate(2), getDate(3));
        var averages = averageCpRepository.findByYrmonInAndCitIn(dates, citiesUids);
        var citiesCo = new ArrayList<String>();
        var citiesPm10 = new ArrayList<String>();
        cities.forEach(city -> {
            var cityAverages = averages.stream()
                    .filter(average -> average.getCit().equals(city.getCityId()))
                    .sorted(Comparator.comparing(AverageCP::getYrmon))
                    .toList();
            if (cityAverages.size() != FULL_MONTH_COUNT) {
                return;
            }
            if (cityAverages.get(0).getAvCo() < cityAverages.get(1).getAvCo() && cityAverages.get(1).getAvCo() < cityAverages.get(2).getAvCo()) {
                citiesCo.add(city.getCityName());
            }
            if (cityAverages.get(0).getAvPm10() < cityAverages.get(1).getAvPm10() && cityAverages.get(1).getAvPm10() < cityAverages.get(2).getAvPm10()) {
                citiesPm10.add(city.getCityName());
            }
        });
        return M3Dto.of(citiesCo, citiesPm10);
    }

    private String getDate(final int backMonths) {
        var date = LocalDateTime.now().minusMonths(backMonths);
        return date.getYear() + (date.getMonthValue() < 10 ? "0" : "") + date.getMonthValue();
    }

    public H1CityDto get1HCity(final UUID cityId) {
        var measurements = measureRepository.findByCityIdAndTimestampAfter(cityId, LocalDateTime.now().minusHours(1));
        var statsNO2 = getH1Stats(measurements, Factor.NO2);
        var statsCO = getH1Stats(measurements, Factor.CO);
        var statsPM10 = getH1Stats(measurements, Factor.PM10);
        return H1CityDto.of(
                statsNO2.getAvg(), statsNO2.getMax(), statsNO2.getMin(),
                statsCO.getAvg(), statsCO.getMax(), statsCO.getMin(),
                statsPM10.getAvg(), statsPM10.getMax(), statsPM10.getMin());
    }

    private H1Stats getH1Stats(final List<Measure> measurements, final Factor factor) {
        var min = measurements.stream()
                .map(measure -> getFactorField(factor, measure))
                .min(Comparator.naturalOrder())
                .orElse((float) 0);
        var max = measurements.stream()
                .map(measure -> getFactorField(factor, measure))
                .max(Comparator.naturalOrder())
                .orElse((float) 0);
        var avg = (float) measurements.stream()
                .map(measure -> getFactorField(factor, measure))
                .mapToDouble(Float::doubleValue)
                .average()
                .orElse(0);
        return H1Stats.of(min.toString(), max.toString(), String.valueOf(avg));
    }

    private static Float getFactorField(final Factor factor, final Measure measure) {
        return switch (factor) {
            case PM10 -> measure.getPm10();
            case CO -> measure.getCo();
            case NO2 -> measure.getNo2();
        };
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static final class H1Stats {
        String min;
        String max;
        String avg;

        public static H1Stats of(String min, String max, String avg) {
            return new H1Stats(min, max, avg);
        }
    }

    private enum Factor {
        PM10,
        CO,
        NO2
    }
}
