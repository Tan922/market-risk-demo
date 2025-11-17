package com.example.marketrisk.repository;

import com.example.marketrisk.model.entity.RegulatoryReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegulatoryReportRepository extends JpaRepository<RegulatoryReportEntity, Long> {

    List<RegulatoryReportEntity> findByReportDate(LocalDate date);

    List<RegulatoryReportEntity> findByReportType(String reportType);

    List<RegulatoryReportEntity> findByExported(boolean exported);

}
