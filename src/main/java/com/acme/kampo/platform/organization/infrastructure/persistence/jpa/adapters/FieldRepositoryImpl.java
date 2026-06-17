package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.organization.domain.model.aggregates.Field;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FundoId;
import com.acme.kampo.platform.organization.domain.repositories.FieldRepository;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.assemblers.FieldPersistenceAssembler;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.repositories.FieldJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link FieldRepository} contract using Spring Data JPA.
 */
@Repository
public class FieldRepositoryImpl implements FieldRepository {

    private final FieldJpaRepository jpaRepository;

    public FieldRepositoryImpl(FieldJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Field save(Field field) {
        var entity = FieldPersistenceAssembler.toPersistenceFromDomain(field);
        var saved  = jpaRepository.save(entity);
        return FieldPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<Field> findById(FieldId id) {
        return jpaRepository.findById(id.getValue())
                .map(FieldPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Field> findByFundoId(FundoId fundoId) {
        return jpaRepository.findByFundoId(fundoId.getValue()).stream()
                .map(FieldPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsById(FieldId id) {
        return jpaRepository.existsById(id.getValue());
    }
}