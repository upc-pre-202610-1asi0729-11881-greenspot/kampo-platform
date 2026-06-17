package com.acme.kampo.platform.field.application.internal.queryservices;

import com.acme.kampo.platform.field.application.queryservices.ObservationQueryService;
import com.acme.kampo.platform.field.domain.model.aggregates.Observation;
import com.acme.kampo.platform.field.domain.model.queries.GetObservationByIdQuery;
import com.acme.kampo.platform.field.domain.model.queries.GetObservationsByFieldVisitQuery;
import com.acme.kampo.platform.field.domain.repositories.ObservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link ObservationQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class ObservationQueryServiceImpl implements ObservationQueryService {

    private final ObservationRepository observationRepository;

    public ObservationQueryServiceImpl(ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @Override
    public Optional<Observation> handle(GetObservationByIdQuery query) {
        return observationRepository.findById(query.observationId());
    }

    @Override
    public List<Observation> handle(GetObservationsByFieldVisitQuery query) {
        return observationRepository.findByFieldVisitId(query.fieldVisitId());
    }
}