package com.air.quality.service;

import com.air.quality.dto.stats.M3Dto;
import com.air.quality.repository.TestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class StatsService {

    final TestRepository testRepository;

    public M3Dto get3M(final String regionId) { // TODO ??? Dto albo domenowy obiekt
        var q = testRepository.findAll(); // TODO
        return M3Dto.of(List.of("aaa", "bbb"), List.of("ccc", "ddd")); // TODO
    }
}
