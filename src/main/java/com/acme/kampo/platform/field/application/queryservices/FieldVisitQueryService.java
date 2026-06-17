package com.acme.kampo.platform.field.application.queryservices;

import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;
import com.acme.kampo.platform.field.domain.model.queries.GetFieldVisitByIdQuery;
import com.acme.kampo.platform.field.domain.model.queries.GetFieldVisitsByFieldQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link FieldVisit} read operations.
 */
public interface FieldVisitQueryService {

    Optional<FieldVisit> handle(GetFieldVisitByIdQuery query);

    List<FieldVisit> handle(GetFieldVisitsByFieldQuery query);
}