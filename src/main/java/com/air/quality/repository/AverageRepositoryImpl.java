package com.air.quality.repository;

import com.air.quality.entity.Average;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AverageRepositoryImpl extends JpaRepository<Average, String> {

    List<Average> findByYrmonInAndCitIn(final List<String> yearMonth, final List<UUID> cityId);

    List<Average> findTop100ByYrmonOrderByAvPm10Desc(final String yearMonth);
}
