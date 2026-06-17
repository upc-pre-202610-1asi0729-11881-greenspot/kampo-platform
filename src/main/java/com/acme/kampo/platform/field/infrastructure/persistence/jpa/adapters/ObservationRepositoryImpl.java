package com.acme.kampo.platform.field.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.field.domain.model.aggregates.Observation;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;
import com.acme.kampo.platform.field.domain.model.valueobjects.ObservationId;
import com.acme.kampo.platform.field.domain.repositories.ObservationRepository;
import com.acme.kampo.platform.field.infrastructure.persistence.jpa.assemblers.ObservationPersistenceAssembler;
import com.acme.kampo.platform.field.infrastructure.persistence.jpa.repositories.ObservationJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link ObservationRepository} contract using Spring Data JPA.
 * Publishes {@link com.acme.kampo.platform.fieldoperation.domain.model.events.ObservationRegisteredEvent}
 * after a new Observation is persisted.
 */
@Repository
public class ObservationRepositoryImpl implements ObservationRepository {

    private final ObservationJpaRepository  jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ObservationRepositoryImpl(ObservationJpaRepository jpaRepository,
                                     ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Observation save(Observation observation) {
        boolean isNew    = observation.getId() == null;
        var entity       = ObservationPersistenceAssembler.toPersistenceFromDomain(observation);
        var saved        = jpaRepository.save(entity);
        var savedDomain  = ObservationPersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            savedDomain.domainEvents().forEach(eventPublisher::publishEvent);
            savedDomain.clearDomainEvents();
        }
        return savedDomain;
    }

    @Override
    public Optional<Observation> findById(ObservationId id) {
        return jpaRepository.findById(id.getValue())
                .map(ObservationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Observation> findByFieldVisitId(FieldVisitId fieldVisitId) {
        return jpaRepository.findByFieldVisitId(fieldVisitId.getValue()).stream()
                .map(ObservationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsById(ObservationId id) {
        return jpaRepository.existsById(id.getValue());
    }
}