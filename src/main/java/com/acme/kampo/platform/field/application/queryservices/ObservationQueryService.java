package com.acme.kampo.platform.field.application.queryservices;

import com.acme.kampo.platform.field.domain.model.aggregates.Observation;
import com.acme.kampo.platform.field.domain.model.queries.GetObservationByIdQuery;
import com.acme.kampo.platform.field.domain.model.queries.GetObservationsByFieldVisitQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link Observation} read operations.
 */
public interface ObservationQueryService {

    Optional<Observation> handle(GetObservationByIdQuery query);

    List<Observation> handle(GetObservationsByFieldVisitQuery query);
}