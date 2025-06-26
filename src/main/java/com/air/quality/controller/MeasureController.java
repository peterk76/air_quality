package com.air.quality.controller;

import com.air.quality.dto.measure.MeasureDto;
import com.air.quality.service.MeasureService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class MeasureController {

    final MeasureService measureService;

    @PostMapping(value = "save-measure")
    public boolean saveMeasure(@RequestBody MeasureDto measure) {
        log.info("[Controller] SaveController.saveMeasure: measure [{}]", measure.toString());
        return measureService.saveNew(measure);
    }
}
