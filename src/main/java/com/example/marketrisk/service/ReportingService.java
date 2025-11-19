package com.example.marketrisk.service;

import com.example.marketrisk.model.entity.RegulatoryReportEntity;
import com.example.marketrisk.repository.RegulatoryReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportingService {

    private final RegulatoryReportRepository repository;

    // ------------------------------------------------------------
    // Fetch by report date
    // ------------------------------------------------------------
    public List<RegulatoryReportEntity> getReportsByDate(LocalDate date) {
        return repository.findByReportDate(date);
    }

    // ------------------------------------------------------------
    // Fetch by report type
    // ------------------------------------------------------------
    public List<RegulatoryReportEntity> getReportsByType(String reportType) {
        return repository.findByReportType(reportType);
    }

    // ------------------------------------------------------------
    // Fetch by name
    // ------------------------------------------------------------
    public List<RegulatoryReportEntity> getReportsByName(String name) {
        return repository.findByName(name);
    }

    // ------------------------------------------------------------
    // Fetch reports for a name within a timestamp range, ordered
    // ------------------------------------------------------------
    public List<RegulatoryReportEntity> getReportsByNameAndTimeRange(
            String name,
            long startTimestamp,
            long endTimestamp
    ) {
        return repository.findByNameAndTimestampBetweenOrderByTimestamp(name, startTimestamp, endTimestamp);
    }

    // ------------------------------------------------------------
    // Fetch the latest report for a name
    // ------------------------------------------------------------
    public Optional<RegulatoryReportEntity> getLatestReportByName(String name) {
        return repository.findByNameAndTimestampBetweenOrderByTimestamp(
                name,
                0L,
                System.currentTimeMillis()
        ).stream().reduce((first, second) -> second); // pick last (latest)
    }

    // ------------------------------------------------------------
    // Save / update report
    // ------------------------------------------------------------
    @Transactional
    public RegulatoryReportEntity saveReport(RegulatoryReportEntity report) {
        return repository.save(report);
    }

    // ------------------------------------------------------------
    // Delete a report by ID
    // ------------------------------------------------------------
    @Transactional
    public void deleteReport(Long id) {
        repository.deleteById(id);
    }

    // ------------------------------------------------------------
    // Count reports by type
    // ------------------------------------------------------------
    public long countByType(String reportType) {
        return repository.findByReportType(reportType).size();
    }
}
