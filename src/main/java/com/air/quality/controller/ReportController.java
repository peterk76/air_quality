package com.air.quality.controller;

import com.air.quality.dto.report.WorstCityNo2y2yDto;
import com.air.quality.service.ReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/report")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class ReportController {

    final ReportService reportService;

    @GetMapping(value = "worst-cities-no2-y2y")
    public List<WorstCityNo2y2yDto> getWorstCitiesNo2y2y() {
        log.info("[Controller] ReportController.getWorstCitiesNo2y2y");
        return reportService.getWorstCitiesNo2y2y();
    }
}
