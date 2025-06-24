package com.air.quality.service;

import com.air.quality.dto.report.WorstCityNo2y2yDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class ReportService {

    public List<WorstCityNo2y2yDto> getWorstCitiesNo2y2y() {
        return List.of( // TODO
                WorstCityNo2y2yDto.of("1", "2", "3", "4", "5"),
                WorstCityNo2y2yDto.of("11", "22", "33", "44", "55")
        );
    }
}
