package com.air.quality.repository;

import com.air.quality.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, String> {
}
