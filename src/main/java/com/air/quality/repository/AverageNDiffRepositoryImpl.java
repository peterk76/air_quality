package com.air.quality.repository;

import com.air.quality.entity.AverageNDiff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AverageNDiffRepositoryImpl extends JpaRepository<AverageNDiff, String> {

    List<AverageNDiff> findByYrmon(final String yrmon);

}
