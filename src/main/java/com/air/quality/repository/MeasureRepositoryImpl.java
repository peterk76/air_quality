package com.air.quality.repository;

import com.air.quality.entity.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MeasureRepositoryImpl extends JpaRepository<Measure, String> {

    List<Measure> findByCityIdAndTimestampAfter(final UUID cityId, final LocalDateTime timestamp);

}
