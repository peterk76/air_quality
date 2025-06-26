package com.air.quality.service;

import com.air.quality.dto.report.WorstCityNo2y2yDto;
import com.air.quality.model.City;
import com.air.quality.repository.AverageCpRepositoryImpl;
import com.air.quality.repository.AverageNDiffRepositoryImpl;
import com.opencsv.CSVWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class ReportService {

    static final String[] HEADER = {"CITY", "REGION", "PM10"};
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");
    static final String UNKNOWN_CITY = "Unknown City";

    final CityService cityService;
    final AverageCpRepositoryImpl averageCpRepository;
    final AverageNDiffRepositoryImpl averageNDiffRepository;

    @Value("${report.directory}")
    String directory;

    @Scheduled(cron = "0 0 1 * * ?")
    public void reportCurrentTime() {
        var yearMonth = LocalDateTime.now().minusDays(1).format(FORMATTER);
        var csvFile = directory + "/WORST_CITIES_PM10_" + yearMonth + ".csv";
        var topPm10 = averageCpRepository.findTop100ByYrmonOrderByAvPm10Desc(yearMonth);
        var cities = cityService.getAllCities();
        var rows = topPm10.stream()
                .map(avg -> {
                    var city = cities.stream()
                            .filter(cit -> cit.getCityId().equals(avg.getCit()))
                            .findFirst();
                    return new String[]{
                            city.map(City::getCityName).orElse(UNKNOWN_CITY),
                            city.map(City::getRegion).orElse(UNKNOWN_CITY),
                            avg.getAvPm10().toString()};
                })
                .toList();
        writeCsvFile(csvFile, rows);
    }

    private static void writeCsvFile(final String filePath, final List<String[]> data) {
        try (var writer = new CSVWriter(new FileWriter(filePath), ',', NO_QUOTE_CHARACTER, '"', "\n")) {
            writer.writeNext(HEADER);
            data.forEach(writer::writeNext);
        } catch (IOException e) {
            log.error("Error while writing CSV file", e);
        }
    }

    public List<WorstCityNo2y2yDto> getWorstCitiesNo2y2y() {
        var yearMonth = LocalDateTime.now().minusMonths(1).format(FORMATTER);
        return averageNDiffRepository.findByYrmon(yearMonth).stream()
                .map(averages -> {
                    var city = cityService.getCityById(averages.getCit());
                    return WorstCityNo2y2yDto.of(
                            city.map(City::getCityName).orElse(UNKNOWN_CITY),
                            averages.getCit().toString(),
                            city.map(City::getCountry).orElse(UNKNOWN_CITY),
                            averages.getAvNo2Curr().toString(),
                            averages.getAvNo2Prev().toString());
                })
                .toList();
    }
}
