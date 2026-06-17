package com.acme.kampo.platform.field.application.internal.queryservices;

import com.acme.kampo.platform.field.application.queryservices.FieldVisitQueryService;
import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;
import com.acme.kampo.platform.field.domain.model.queries.GetFieldVisitByIdQuery;
import com.acme.kampo.platform.field.domain.model.queries.GetFieldVisitsByFieldQuery;
import com.acme.kampo.platform.field.domain.repositories.FieldVisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link FieldVisitQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class FieldVisitQueryServiceImpl implements FieldVisitQueryService {

    private final FieldVisitRepository fieldVisitRepository;

    public FieldVisitQueryServiceImpl(FieldVisitRepository fieldVisitRepository) {
        this.fieldVisitRepository = fieldVisitRepository;
    }

    @Override
    public Optional<FieldVisit> handle(GetFieldVisitByIdQuery query) {
        return fieldVisitRepository.findById(query.fieldVisitId());
    }

    @Override
    public List<FieldVisit> handle(GetFieldVisitsByFieldQuery query) {
        return fieldVisitRepository.findByFieldId(query.fieldId());
    }
}