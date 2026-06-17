package com.acme.kampo.platform.field.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;
import com.acme.kampo.platform.field.domain.repositories.FieldVisitRepository;
import com.acme.kampo.platform.field.infrastructure.persistence.jpa.assemblers.FieldVisitPersistenceAssembler;
import com.acme.kampo.platform.field.infrastructure.persistence.jpa.repositories.FieldVisitJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link FieldVisitRepository} contract using Spring Data JPA.
 * Publishes domain events after a FieldVisit is completed (status changes to DONE).
 */
@Repository
public class FieldVisitRepositoryImpl implements FieldVisitRepository {

    private final FieldVisitJpaRepository   jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FieldVisitRepositoryImpl(FieldVisitJpaRepository jpaRepository,
                                    ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public FieldVisit save(FieldVisit fieldVisit) {
        boolean isNew  = fieldVisit.getId() == null;
        var entity     = FieldVisitPersistenceAssembler.toPersistenceFromDomain(fieldVisit);
        var saved      = jpaRepository.save(entity);
        var domain     = FieldVisitPersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            domain.domainEvents().forEach(eventPublisher::publishEvent);
            domain.clearDomainEvents();
        } else {
            // publish events from update (e.g. FieldVisitCompletedEvent)
            fieldVisit.domainEvents().forEach(eventPublisher::publishEvent);
            fieldVisit.clearDomainEvents();
        }
        return domain;
    }

    @Override
    public Optional<FieldVisit> findById(FieldVisitId id) {
        return jpaRepository.findById(id.getValue())
                .map(FieldVisitPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<FieldVisit> findByFieldId(FieldId fieldId) {
        return jpaRepository.findByFieldId(fieldId.getValue()).stream()
                .map(FieldVisitPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsById(FieldVisitId id) {
        return jpaRepository.existsById(id.getValue());
    }
}