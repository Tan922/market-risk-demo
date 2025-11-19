package com.example.marketrisk.repository;

import com.example.marketrisk.model.entity.RegulatoryReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegulatoryReportRepository extends JpaRepository<RegulatoryReportEntity, Long> {

    // -------------------------------------------------------------------------
    //   Basic Queries
    // -------------------------------------------------------------------------

    List<RegulatoryReportEntity> findByReportDate(LocalDate date);

    List<RegulatoryReportEntity> findByReportType(String reportType);

    List<RegulatoryReportEntity> findByName(String name);

    List<RegulatoryReportEntity> findByNameAndTimestampBetweenOrderByTimestamp(
            String name,
            long start,
            long end
    );

}
