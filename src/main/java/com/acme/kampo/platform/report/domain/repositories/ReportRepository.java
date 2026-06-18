package com.acme.kampo.platform.report.domain.repositories;

import com.acme.kampo.platform.report.domain.model.aggregates.Report;
import com.acme.kampo.platform.report.domain.model.valueobjects.ReportId;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {

    Optional<Report> findById(ReportId reportId);
    List<Report> findAll();
    Report save(Report report);
}