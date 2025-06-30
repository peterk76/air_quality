package com.air.quality.controller;

import com.air.quality.dto.stats.H1CityDto;
import com.air.quality.dto.stats.M3Dto;
import com.air.quality.service.StatsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/stats")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class StatsController {

    final StatsService statsService;

    @GetMapping(value = "3M/{regionId}")
    public M3Dto get3M(@PathVariable String regionId) {
        log.info("[Controller] StatsController.get3M: regionId [{}]", regionId);
        return statsService.get3M(UUID.fromString(regionId));
    }

    @GetMapping(value = "1H/city/{cityId}")
    public H1CityDto get1HCity(@PathVariable UUID cityId) {
        log.info("[Controller] StatsController.get1HCity: cityId [{}]", cityId);
        return statsService.get1HCity(cityId);
    }
}
