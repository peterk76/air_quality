package com.air.quality.controller;

import com.air.quality.dto.stats.M3Dto;
import com.air.quality.service.StatsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        return statsService.get3M(regionId);
    }
}
