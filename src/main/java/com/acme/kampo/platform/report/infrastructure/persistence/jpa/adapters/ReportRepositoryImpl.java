package com.acme.kampo.platform.report.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.report.domain.model.aggregates.Report;
import com.acme.kampo.platform.report.domain.repositories.ReportRepository;
import com.acme.kampo.platform.report.domain.model.valueobjects.ReportId;
import com.acme.kampo.platform.report.infrastructure.persistence.jpa.assemblers.ReportPersistenceAssembler;
import com.acme.kampo.platform.report.infrastructure.persistence.jpa.repositories.ReportJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReportRepositoryImpl implements ReportRepository {

    private final ReportJpaRepository reportJpaRepository;

    public ReportRepositoryImpl(ReportJpaRepository reportJpaRepository) {
        this.reportJpaRepository = reportJpaRepository;
    }

    @Override
    public Optional<Report> findById(ReportId reportId) {
        return reportJpaRepository.findById(reportId.getValue())
                .map(ReportPersistenceAssembler::toDomain);
    }

    @Override
    public List<Report> findAll() {
        return reportJpaRepository.findAll()
                .stream()
                .map(ReportPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public Report save(Report report) {
        var entity = ReportPersistenceAssembler.toEntity(report);
        var saved = reportJpaRepository.save(entity);
        return ReportPersistenceAssembler.toDomain(saved);
    }
}
