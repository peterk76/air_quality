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
import java.time.ZoneId;

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
}
