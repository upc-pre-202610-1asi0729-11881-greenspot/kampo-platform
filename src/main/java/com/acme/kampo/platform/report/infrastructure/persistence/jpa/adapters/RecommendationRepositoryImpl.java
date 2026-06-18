package com.acme.kampo.platform.report.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.report.domain.model.aggregates.Recommendation;
import com.acme.kampo.platform.report.domain.repositories.RecommendationRepository;
import com.acme.kampo.platform.report.domain.model.valueobjects.RecommendationId;
import com.acme.kampo.platform.report.domain.model.valueobjects.ReportId;
import com.acme.kampo.platform.report.infrastructure.persistence.jpa.assemblers.RecommendationPersistenceAssembler;
import com.acme.kampo.platform.report.infrastructure.persistence.jpa.repositories.RecommendationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecommendationRepositoryImpl implements RecommendationRepository {

    private final RecommendationJpaRepository recommendationJpaRepository;

    public RecommendationRepositoryImpl(RecommendationJpaRepository recommendationJpaRepository) {
        this.recommendationJpaRepository = recommendationJpaRepository;
    }

    @Override
    public Optional<Recommendation> findById(RecommendationId recommendationId) {
        return recommendationJpaRepository.findById(recommendationId.getValue())
                .map(RecommendationPersistenceAssembler::toDomain);
    }

    @Override
    public List<Recommendation> findByReportId(ReportId reportId) {
        return recommendationJpaRepository.findAllByReportId(reportId.getValue())
                .stream()
                .map(RecommendationPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public Recommendation save(Recommendation recommendation) {
        var entity = RecommendationPersistenceAssembler.toEntity(recommendation);
        var saved = recommendationJpaRepository.save(entity);
        return RecommendationPersistenceAssembler.toDomain(saved);
    }
}