package com.air.quality.repository;

import com.air.quality.entity.AverageCP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AverageCpRepositoryImpl extends JpaRepository<AverageCP, String> {

    List<AverageCP> findByYrmonInAndCitIn(final List<String> yearMonth, final List<UUID> cityId);

    List<AverageCP> findTop100ByYrmonOrderByAvPm10Desc(final String yearMonth);
}
