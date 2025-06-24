package com.air.quality.service;

import com.air.quality.dto.measure.MeasureDto;
import com.air.quality.entity.Measure;
import com.air.quality.repository.MeasureRepositoryImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeasureService {

    final MeasureRepositoryImpl measureRepository;

    @Transactional
    public boolean saveNew(final MeasureDto measureDto) {
        var measure = Measure.of(
                measureDto.getSensorId(),
                measureDto.getCityId(),
                measureDto.getPm10(),
                measureDto.getCo(),
                measureDto.getNo2(),
                LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(measureDto.getTimestamp()),
                        ZoneId.systemDefault())
        );
        measureRepository.save(measure);
        return true;
    }

    public void saveFake1() { // TODO delete
        IntStream.rangeClosed(2025, 2025).forEach(year -> {
            IntStream.rangeClosed(6, 6).forEach(month -> {
                IntStream.rangeClosed(1, 200).forEach(city -> {
                    var measure = Measure.of(
                            UUID.randomUUID(),
                            UUID.fromString("00000000-0000-0000-0000-000000000" + String.format("%03d", city)),
                            city,
                            0,
                            0,
                            LocalDateTime.of(year, month, 1, 0, 0, 0, 0) // First day of the month
                    );
                    measureRepository.save(measure);
                    measure = Measure.of(
                            UUID.randomUUID(),
                            UUID.fromString("00000000-0000-0000-0000-000000000" + String.format("%03d", city)),
                            city + 1,
                            0,
                            0,
                            LocalDateTime.of(year, month, YearMonth.of(year, month).atEndOfMonth().getDayOfMonth() , 0, 0, 0, 0) // last day of the month
                    );
                    measureRepository.save(measure);
                });
            });
        });
    }

    public void saveFake() { // TODO delete
        //YearMonth.of(2019,7).atEndOfMonth().getDayOfMonth()
        IntStream.rangeClosed(2024, 2025).forEach(year -> {
            IntStream.rangeClosed(1, 12).forEach(month -> {
                var measure = Measure.of(
                        UUID.randomUUID(),
                        UUID.fromString("00000000-0000-0000-0000-000000000001"),
                        0,
                        month,
                        year,
                        LocalDateTime.of(year, month, 1, 0, 0, 0, 0) // First day of the month
                );
                measureRepository.save(measure);
                measure = Measure.of(
                        UUID.randomUUID(),
                        UUID.fromString("00000000-0000-0000-0000-000000000001"),
                        0,
                        month + 1,
                        year + 1,
                        LocalDateTime.of(year, month, YearMonth.of(year, month).atEndOfMonth().getDayOfMonth() , 0, 0, 0, 0) // last day of the month
                );
                measureRepository.save(measure);

                measure = Measure.of(
                        UUID.randomUUID(),
                        UUID.fromString("00000000-0000-0000-0000-000000000002"),
                        0,
                        month,
                        year,
                        LocalDateTime.of(year, month, 1, 0, 0, 0, 0) // First day of the month
                );
                measureRepository.save(measure);
                measure = Measure.of(
                        UUID.randomUUID(),
                        UUID.fromString("00000000-0000-0000-0000-000000000002"),
                        0,
                        month + 1,
                        year + 1,
                        LocalDateTime.of(year, month, YearMonth.of(year, month).atEndOfMonth().getDayOfMonth(), 0, 0, 0, 0) // last day of the month
                );
                measureRepository.save(measure);
            });
        });
    }
}
