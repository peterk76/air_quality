package com.air.quality.service;

import com.air.quality.dto.report.WorstCityNo2y2yDto;
import com.air.quality.model.City;
import com.air.quality.repository.AverageRepositoryImpl;
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

    final CityService cityService;
    final AverageRepositoryImpl averageRepository;

    static final String[] HEADER = {"CITY", "REGION", "PM10"};
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    @Value("${report.directory}")
    String directory;

    //@Scheduled(cron = "0/10 * * * * ?") // TODO
    @Scheduled(cron = "0 0 1 * * ?")
    public void reportCurrentTime() {
        var yearMonth = LocalDateTime.now().minusDays(1).format(FORMATTER);
        var csvFile = directory + "/WORST_CITIES_PM10_" + yearMonth + ".csv";
        var topPm10 = averageRepository.findTop100ByYrmonOrderByAvPm10Desc(yearMonth);
        var cities = cityService.getAllCities();
        var rows = topPm10.stream()
                .map(avg -> {
                    var city = cities.stream()
                            .filter(cit -> cit.getCityId().equals(avg.getCit()))
                            .findFirst();
                    return new String[]{
                            city.map(City::getCity).orElse("Unknown City"),
                            city.map(City::getRegion).orElse("Unknown City"),
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
        return List.of( // TODO
                WorstCityNo2y2yDto.of("1", "2", "3", "4", "5"),
                WorstCityNo2y2yDto.of("11", "22", "33", "44", "55")
        );
    }
}
